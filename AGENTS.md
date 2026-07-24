# Sportmonks Library - Agent Instructions

## Project overview

Java library (Java 8) that wraps the Sportmonks football data API. Published on Maven Central as `es.com.kete1987:sportmonks.library`. Supports both API v2 and v3.

## Build

```
mvn clean package        # compile + tests
mvn clean deploy         # publish to Maven Central (requires GPG key + credentials)
```

No test suite exists — `APITests.java` is a manual scratch file, not JUnit. Do not run it as part of CI.

## Key structure

```
src/main/java/es/com/kete1987/sportmonks/library/
├── SportMonksAPIV2.java       # entry point for Sportmonks API v2
├── SportMonksAPIV3.java       # entry point for Sportmonks API v3
├── common/
│   ├── net/HttpFunctions.java # raw HTTP GET
│   ├── net/GetResponse.java   # HTTP response wrapper (code + body + rate-limit headers)
│   └── util/
│       ├── Constants.java     # base URLs for v2 and v3
│       ├── SportMonksException.java
│       └── EmptyStringToNumberTypeAdapter.java  # Gson fix: API sometimes returns "" for ints
├── v2/model/                  # Gson POJOs for v2 responses
└── v3/model/                  # Gson POJOs for v3 responses
```

## API entry points

Both classes are **singletons**:

```java
SportMonksAPIV2 api = SportMonksAPIV2.getInstance("YOUR_API_KEY");
SportMonksAPIV3 api = SportMonksAPIV3.getInstance("YOUR_API_KEY");
```

Rate-limit headers are available after any call:
```java
api.getRemainingRequests();
api.getMaximumRequests();
```

All methods throw `IOException` and `SportMonksException`. `SportMonksException` wraps non-200 HTTP responses with the status code and response body.

## Pagination

List endpoints handle pagination automatically — the library iterates all pages internally and returns a complete list. This means a single call may fire multiple HTTP requests.

Everything goes through one helper, `SportMonksApiBase.fetchPaged()`. Never write a pagination loop inline in `FootballApi`/`CoreApi`/`OddsApi`: add the call there instead, passing the `*Response` class plus its `getData`/`getPagination` method refs.

**Cursor first, offset as fallback.** The API offers two ways forward, both reported inside `pagination`:

- `next_cursor` (recommended by Sportmonks since 2026-06-05) — no depth limit and faster under load. It arrives as a **full URL without the API token and without the original query**, so following it verbatim would fail authentication and drop the `include`/filter params. Only the opaque `cursor` value is extracted and re-applied to the URL the library built. Never parse the cursor itself.
- `next_page` / `&page=N` — used only when the endpoint returns no `next_cursor`.

**Offset depth limit.** Since 2026-06-23 the API rejects offset pagination past 20.000 rows (page 801 with `per_page=25`, page 401 with `per_page=50`). When walking by offset, `fetchPaged` stops right before crossing it and returns what it has gathered, rather than letting the request 4xx. The result is silently truncated in that case — endpoints large enough to hit it should be paginated by cursor, which has no such limit.

**`limit`.** A positive `limit` shrinks `per_page` (capped at 50) so the first page already carries enough rows, stops as soon as `limit` is reached, and trims the overflow. It works the same on both cursor and offset walks. A non-positive `limit` means "fetch everything".

## Gson / type adapters

- `EmptyStringToNumberTypeAdapter`: registered for `int` and `Integer` — the API occasionally returns `""` where an int is expected.
- `PlayerDataAdapter` (v2 only): custom deserializer for `PlayerData`.
- `SubscriptionMetaDeserializer` (v3 only): custom deserializer for `SubscriptionMeta`.

Always register these adapters when adding new `GsonBuilder` usages — don't use `new Gson()` directly if the response may contain int fields.

## Adding new endpoints

1. Add model POJOs under `v2/model/` or `v3/model/` mirroring the JSON structure.
2. Add the method to `SportMonksAPIV2` or `SportMonksAPIV3`.
3. Build the URL using `Constants.baseURLV2` or `Constants.baseURLV3`, append `?api_token=` + apiKey, then `&include=` params.
4. Call `HttpFunctions.get(url)`, check `response.getResponseCode() == Constants.RESPONSE_OK`, deserialize with Gson, throw `SportMonksException` on error.
5. Call `updateHeaders(response)` after every HTTP call to keep rate-limit state up to date.

## Publishing a new version

1. Update `<version>` in `pom.xml`.
2. Run `mvn clean deploy` from IntelliJ (Maven panel → Lifecycle → deploy) or terminal.
3. Enter GPG passphrase when prompted (stored in 1Password).
4. Wait 5–15 minutes for `BUILD SUCCESS` — the plugin polls Maven Central until the artifact is published.
5. Published at: https://central.sonatype.com/artifact/es.com.kete1987/sportmonks.library

## Dependencies

| Artifact | Version | Scope |
|---|---|---|
| `com.google.code.gson:gson` | 2.9.0 | compile |
| `org.slf4j:slf4j-api` | 1.7.36 | compile |
| `org.slf4j:slf4j-jdk14` | 1.7.36 | runtime |

