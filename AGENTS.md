# Sportmonks Library - Agent Instructions

## Project overview

Java library (Java 11) that wraps the Sportmonks football data API v3. Published on Maven Central as `es.com.kete1987:sportmonks.library`.

Library 3.0.0 restructured the public API: the `SportMonksAPIV2`/`SportMonksAPIV3` singletons became the `SportMonksAPI` facade plus four sub-APIs, and the model packages moved. See [MIGRATION.md](MIGRATION.md) for the 2.x → 3.x mapping.

## Build

```
mvn test                 # unit tests (MockWebServer, no network)
mvn clean package        # compile + tests + jar
mvn clean deploy         # publish to Maven Central (requires GPG key + credentials)
```

CI (`.github/workflows/ci.yml`) runs `mvn test` on pushes to master and on pull requests, publishes the JUnit report, and analyses the build with SonarCloud.

## Tests

JUnit 5 + OkHttp `MockWebServer`. Every test extends `BaseApiTest`, which starts a mock server and points all five base URLs at it (the odds and odds-catalogue ones under `/v3/football/odds/` and `/v3/odds/` of the mock server, so tests assert the full paths), so no test touches the network. Responses are JSON fixtures under `src/test/resources/fixtures/`, enqueued with `enqueue("name.json")`.

```java
class VenueApiTest extends BaseApiTest {
    @Test
    void getAllVenues_returnsParsedList() throws IOException, SportMonksException {
        enqueue("venues.json");

        List<Venue> venues = api.getAllVenues();

        assertEquals(2, venues.size());
        assertEquals("Old Trafford", venues.get(0).getName());
    }
}
```

`SportMonksIntegrationTest` is the exception: it hits the real API and is excluded from the default surefire run. It only runs under the `integration` profile and skips itself unless `SPORTMONKS_API_KEY` is set:

```
SPORTMONKS_API_KEY=... mvn test -Pintegration
```

The `*IntegrationTest` classes compare real responses with the models through `JsonCoverage` (Gson drops unknown JSON keys silently, so this is how a missing field shows up).

**Not validated against real responses** (the test subscription has no access, so routes exist but models and fields were never compared with real JSON; do not treat them as tested): inplay and premium odds, predictions and probabilities, expected goals per fixture and lineup, news, match facts, rankings, team of the week, and the includes `xGFixture`, `trends`, `pressure`, `ballCoordinates`, `expectedLineups`, `predictedLineups`, `premiumOdds`, `inplayOdds` and `AIOverviews`.

## Key structure

```
src/main/java/es/com/kete1987/sportmonks/library/
├── SportMonksAPI.java         # facade over the four sub-APIs
├── SportMonksApiBase.java     # shared HTTP: auth, Gson, execute(), pagination, rate limits
├── FootballApi.java           # fixtures, leagues, seasons, teams, standings, transfers, ...
├── CoreApi.java               # continents, countries, regions, cities, types, MySportmonks
├── OddsApi.java               # pre-match / inplay / premium odds, markets, bookmakers
├── PredictionsApi.java        # predictions and probabilities
├── RateLimitTracker.java      # rate-limit state, per entity
├── ClientOptions.java         # timeouts, own OkHttpClient, opt-in retries
├── RetryInterceptor.java      # 429/5xx retries (only with ClientOptions.maxRetries > 0)
├── common/
│   ├── model/                 # pagination, ratelimit, subscription
│   └── util/
│       ├── Constants.java     # the five v3 base URLs (football, odds, odds catalogue, core, my)
│       ├── ApiLocale.java     # locale codes accepted by the API
│       ├── SportMonksException.java
│       ├── ModelCollections.java             # unmodifiable views for model getters
│       └── EmptyStringToNumberTypeAdapter.java  # Gson fix: API sometimes returns "" for ints
├── core/model/                # Gson POJOs for the core endpoints
├── football/model/            # Gson POJOs for the football endpoints
├── football/util/             # type-id constants (EventType, StatisticsType, LineupDetailType, ...)
├── odds/model/                # Gson POJOs for the odds endpoints
└── odds/util/                 # MarketIds: ids of the most used odds markets

src/test/java/es/com/kete1987/sportmonks/library/
├── BaseApiTest.java           # MockWebServer base class
├── JsonCoverage.java          # integration helper: reports JSON keys a model class does not declare
├── *IntegrationTest.java      # real API, only under -Pintegration (see above)
└── ...                        # mock-server tests (e.g. ClientOptionsTest, RetryApiTest)
```

## API entry points

Plain constructors — no singletons. `SportMonksAPI` is a facade that delegates every method to one of the four sub-APIs; the sub-APIs also work standalone if you only need one domain.

```java
SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY");
SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY", ApiLocale.SPANISH);
SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY", ApiLocale.SPANISH, "Europe/Madrid");

FootballApi football = api.getFootball();   // or new FootballApi("YOUR_API_KEY")
```

The optional `locale` and `timezone` are appended to every request built by that instance.

Odds live under two different roots: the bookmaker and market catalogue (`getAllBookmakers`, `getAllMarkets`, ...) is under `/v3/odds/` (`Constants.BASE_URL_ODDS_CATALOG`), while fixture odds (pre-match, inplay, premium) are under `/v3/football/odds/` (`Constants.BASE_URL_ODDS`). `OddsApi` builds catalogue URLs with `catalogUrl(path)` and fixture-odds URLs with `oddsUrl(path)`; using the wrong one returns 404.

The API token travels in the `Authorization` header, injected by an OkHttp interceptor in `buildHttpClient()` — never as an `api_token` query parameter.

Rate limits are available after any call:
```java
api.getRemainingRequests();      // X-RateLimit-Remaining header
api.getMaximumRequests();        // X-RateLimit-Limit header
api.getLastRateLimit();          // rate_limit object of the last response
api.getRateLimitsByEntity();     // latest rate_limit seen per requested_entity
```

All methods throw `IOException` and `SportMonksException`. `SportMonksException` wraps non-2xx HTTP responses and 2xx bodies that cannot be parsed (Gson errors never escape unchecked). Its message stays `"code - body"` (consumers parse it); `getStatusCode()`, `getErrorCode()` (Sportmonks `code`, e.g. 5007) and `getRateLimit()` expose the details.

HTTP settings go through `ClientOptions` (constructors `(apiToken, locale, timezone, ClientOptions)`): timeouts, an own `OkHttpClient`, and opt-in retries for 429/5xx (`RetryInterceptor`, off by default). Without options the client keeps 30 s connect/read timeouts and no retries.

## Pagination

List endpoints handle pagination automatically — the library iterates all pages internally and returns a complete list. This means a single call may fire multiple HTTP requests.

Everything goes through one helper, `SportMonksApiBase.fetchPaged()`. Never write a pagination loop inline in `FootballApi`/`CoreApi`/`OddsApi`: add the call there instead, passing the `*Response` class plus its `getData`/`getPagination` method refs.

**Cursor first, offset as fallback.** The API offers two ways forward, both reported inside `pagination`:

- `next_cursor` (recommended by Sportmonks since 2026-06-05) — no depth limit and faster under load. It arrives as a **full URL without the API token and without the original query**, so following it verbatim would fail authentication and drop the `include`/filter params. Only the opaque `cursor` value is extracted and re-applied to the URL the library built. Never parse the cursor itself.
- `next_page` / `&page=N` — used only when the endpoint returns no `next_cursor`.

**Offset depth limit.** Since 2026-06-23 the API rejects offset pagination past 20.000 rows (page 801 with `per_page=25`, page 401 with `per_page=50`). When walking by offset, `fetchPaged` stops right before crossing it and returns what it has gathered, rather than letting the request 4xx. The result is silently truncated in that case — endpoints large enough to hit it should be paginated by cursor, which has no such limit.

**`limit`.** A positive `limit` shrinks `per_page` (capped at 50) so the first page already carries enough rows, stops as soon as `limit` is reached, and trims the overflow. It works the same on both cursor and offset walks. A non-positive `limit` means "fetch everything", and its first request sends `per_page=50` (half the requests of the API default of 25). In both cases `per_page` goes only on the first request and on offset pages, never alongside a `cursor`.

**Cursor format.** `next_cursor` is read both as a URL (what the API sends today) and as a bare token (what the changelog documents).

## Gson / type adapters

Never build a `Gson` instance directly — call `gson()` from `SportMonksApiBase`, which returns the one shared, immutable instance. It registers:

- `EmptyStringToNumberTypeAdapter` for `int`/`Integer` and, via `forLong()`, for `long`/`Long` — the API occasionally returns `""` where a number is expected.
- `SubscriptionMetaDeserializer` for `SubscriptionMeta`.

## Adding new endpoints

1. Add model POJOs under `core/model/`, `football/model/` or `odds/model/` mirroring the JSON structure. List getters return `ModelCollections.unmodifiable(...)`.
2. Add the method to the sub-API that owns the domain (`FootballApi`, `CoreApi`, `OddsApi`, `PredictionsApi`), then delegate to it from `SportMonksAPI` so the facade stays complete.
3. Build the URL with the sub-API's private `*Url(path, segments...)` helper (it applies locale and timezone) and add includes with `withIncludes(...)`. `path` is the fixed route (`"teams/search"`); every id or user text goes as its own argument (`footballUrl("teams/search", name)`, `oddsUrl("pre-match/fixtures", id, "markets", marketId)`), which percent-encodes it as one segment. Never concatenate values into the path.
4. For a single resource, `get(url, XResponse.class)` (parses once, tracks `rate_limit`, wraps parse errors) and null-check the result: an empty body yields `null`. For a list, call `fetchPaged(...)` — see "Pagination".
5. Add a test extending `BaseApiTest` with a fixture under `src/test/resources/fixtures/`.

## Publishing a new version

Use **Actions → Maven release** for a normal release. Start in `check` mode and use **Release dry run** to verify the signed bundle without uploading. The [release guide](docs/releasing.md) covers credentials, publication and how to resume a failed run.

## Dependencies

| Artifact | Version | Scope |
|---|---|---|
| `org.jetbrains:annotations` | 26.1.0 | compile |
| `com.squareup.okhttp3:okhttp-jvm` | 5.5.1 | compile |
| `com.google.code.gson:gson` | 2.15.0 | compile |
| `org.junit.jupiter:junit-jupiter` | 5.11.0 | test |
| `com.squareup.okhttp3:mockwebserver` | 5.5.1 | test |
