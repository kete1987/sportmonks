# Migration Guide

## 3.5.0 (unreleased)

This release bumps the minor version because of the breaking changes below: `Venue.getZipcode()` now returns `String` and `League.getLastPlayerdAt()`, `getSeasonStatsByParticipant`, `Schedule.getTeamId()` and `TopScoresPlayer.getStage()` are removed, and a response body that cannot be parsed now throws `SportMonksException` instead of `JsonSyntaxException`.

### Football model fixes (#81)

**Breaking: `Venue.getZipcode()` now returns `String`** (it was `Long`). The API sends alphanumeric
postal codes (`"CV6 6GE"`, `"1234 AB"`), so with a `Long` field `getVenues(seasonId)` and every request
with the `venue` include threw `JsonSyntaxException: Expected a long but was CV6 6GE` for UK or Dutch
venues. Callers that used the number must parse the string themselves.

```java
String zip = venue.getZipcode();   // was: Long zip = venue.getZipcode();
```

**Breaking: `League.getLastPlayerdAt()` is removed.** The name had a typo and the getter always returned
`null` because the API sends `last_played_at`; use `League.getLastPlayedAt()`, which now carries the real
value. The two changes are why this release bumps the minor version.

Compatible changes: `Stage.getGamesInCurrentWeek()` keeps returning a `String` but
`Stage.isGamesInCurrentWeek()` gives the boolean the API actually sends. Many models gained fields and
includes that were silently dropped before (see the issue for the list).

### FootballApi routes, pagination and models (#82)

**Season statistics.** `getSeasonStatsByParticipant(id)` called
`statistics/seasons/participants/{id}`, which the API always rejected with 422, so it never
returned data. Use the new `getSeasonStatistics(SeasonStatisticEntity, id)`, which calls
`statistics/seasons/{players|teams|coaches|referees}/{id}` and returns one `SeasonStatistic`
per season (`id`, `teamId`/`playerId`/`coachId`/`refereeId`, `seasonId`, `hasValues`,
`positionId`, `jerseyNumber`) with the values in `getDetails()`, a list of `Statistic`
(`typeId` + `value`).

```java
// before (always failed with 422)
List<Statistic> stats = api.getSeasonStatsByParticipant(676L);
// after
List<SeasonStatistic> seasons = api.getSeasonStatistics(SeasonStatisticEntity.TEAMS, 676L);
Statistic corners = seasons.get(0).getDetailByTypeId(34);
```

**Breaking: `getSeasonStatsByParticipant` is removed.** It never returned data (the route it
called always answered 422), so no working caller can depend on it; use
`getSeasonStatistics(SeasonStatisticEntity.TEAMS, id)` instead.

**`Statistic`** gains `getModelId()` and `getRelationId()` (`statistics/stages|rounds/{id}`).
No getter was removed.

**`Schedule`** now models what `schedules/*` really returns: a stage. New getters `getId()`
(stage id), `getSportId()`, `getLeagueId()`, `getTypeId()`, `getName()`, `getSortOrder()`,
`getFinished()`, `getIsCurrent()`, `getStartingAt()`, `getEndingAt()`,
`getGamesInCurrentWeek()`, `getTieBreakerRuleId()` and `getAggregates()`. `getTeamId()` is removed (**breaking**): the API does not send `team_id`, so it was always `null`.

**Pagination.** These methods used to return only the first 25 rows and now walk every page,
so a single call may fire several requests (and, for catalogue-wide endpoints such as
`getAllCommentaries`, `getAllStandings` or `getAllVenues`, a great many):
`getTeamsByCountry`, `getRefereesByCountry`, `getCoachesByCountry`, `getAllVenues`,
`searchVenues`, `getAllRivals`, `getAllCommentaries`, `getStatsByStage`, `getStatsByRound`,
`getLatestTransfers`, `getTransfersByDateRange`, `searchTeams(name)`, `searchPlayers(name)`,
`searchCoaches`, `searchReferees`, `getRefereesBySeason`, `getAllStandings`,
`getMatchFactsByFixture` and `getMatchFactsByDateRange`. Each one except `getStatsByRound`
and the match facts has a new overload with an `int limit` after its ids (e.g.
`getAllVenues(100)`, `getTeamsByCountry(32L, 100)`) to cap the walk.

**Fixed routes.** `getMatchFactsByFixture` now calls `match-facts/{id}` and
`getMatchFactsByDateRange` calls `match-facts/fixtures/between/{start}/{end}`; the old routes
returned 404.

**New methods.** `getAllFixtures(limit, ...)` (`GET /fixtures`, with includes and filters),
`getTransferRumoursConfig()` and `getVenues(seasonId, includes...)`.

**Null safety.** `getLiveMatches`, `getLatestUpdatedLivescores`, `getLatestUpdatedFixtures`
and `getAllStates` return an empty list instead of `null` (or a `NullPointerException` on an
empty body) when the response carries no `data`; `getStateById` and `getMatchDetail` return
`null` instead of throwing.

### Fixture model (#80)

**Breaking: `TeamMeta.getWinner()` now returns `Boolean`** (it was `String`). The API sends a JSON
boolean and Gson used to leave it as the text `"true"` or `"false"`. The value is nullable; the new
`TeamMeta.isWinner()` returns a primitive `boolean` and treats null as false.

```java
Boolean winner = team.getMeta().getWinner();     // was: String winner = ...; "true".equals(winner)
boolean won = team.getMeta().isWinner();         // null-safe shortcut
```

**Breaking: `EventData` implements `Comparable<EventData>`** (it was `Comparable<Object>`). Comparing
an `EventData` with any other type always threw `ClassCastException`, so only code that named the
raw type is affected.

**Ordering and equality.** The old `compareTo` of `Match`, `Comment`, `Stage` and `EventData` was not
total: it returned 0 for different elements and was not antisymmetric or transitive when keys were
null, so `Collections.sort` could throw "Comparison method violates its general contract" and a
`TreeSet`/`TreeMap` lost elements. Each order is now total, null-safe and consistent with `equals`,
and `equals` of an element without id is identity (it used to be true for any two id-less elements).
The orders that already worked keep their sequence; the ones that were arbitrary are now defined:

- `Match`: state group (live, not started, finished, anything else; a null `state_id` counts as
  anything else), then `starting_at_timestamp` ascending, then `id` ascending, nulls last. Before,
  finished matches and matches with a null state kept the order the API returned them in; now
  finished matches also go by kick-off time, oldest first.
- `Comment`: `order` ascending (a null order first, as before), then `id`.
- `Stage`: `sort_order`, `ending_at`, then `id`, nulls last (a null `ending_at` used to compare as
  greater in both directions).
- `EventData`: penalty shootout events last, then `minute` ascending (null last), `extra_minute`
  (null counts as 0), then `id`. The comparison used `int` subtraction and overflowed with large ids.

Two elements without id that agree on every other key still compare as 0, because nothing tells them
apart.

**New fields (compatible).**
`Match`/`MatchDetail`: `getResultInfo()`, `getDetails()`, `getPlaceholder()`, `getHasOdds()`,
`getHasPremiumOdds()`; `Period.getHasTimer()`; `Odd.getSortOrder()`. `MatchDetail` also exposes the
includes `sport`, `league`, `season`, `stage`, `round`, `group`, `aggregate`, `tvStations` and `odds`
through `getSport()`, `getLeague()`, `getSeason()`, `getStage()`, `getRound()`, `getGroup()`,
`getAggregate()`, `getTvStations()` and `getOdds()`, all nullable (null when the include was not
requested or the fixture has none). `getTvStations()` returns the pivot rows `FixtureTvStation`
(`id`, `fixtureId`, `tvStationId`, `countryId`); the API names the JSON key `tvstations`. They are not
part of the default includes of `getMatchDetail(id)`: `odds` alone adds thousands of rows to a
top-flight fixture, so ask for it explicitly. The includes `premiumOdds`, `inplayOdds`,
`prematchNews`, `postmatchNews`, `predictedLineups`, `matchfacts`, `AIOverviews`, `xGFixture`,
`trends`, `pressure`, `ballCoordinates` and `expectedLineups` are still not modelled in
`MatchDetail`: the test subscription answers 403 for them, so their shape could not be checked.

### HTTP infrastructure and paths (#83, #85)

**Breaking: `TopScoresPlayer.getStage()` is removed** (#81). The API has no `stage` include on
Topscorer (it answers 404, code 5013), so the field was always `null`. Use the `stageId` you
queried with, or fetch the stage with `getStageById`.

**Breaking: unparseable responses throw `SportMonksException`.** A 2xx body that cannot be
parsed (malformed JSON, or a value of the wrong type) used to escape as an unchecked
`JsonSyntaxException`; it is now a `SportMonksException` whose message reads
`"<status> - unparseable response from <url>: <detail>"` and whose cause is the Gson error.
Code that already catches `IOException | SportMonksException` handles it without changes; code
that caught `JsonSyntaxException` must catch `SportMonksException` instead. List methods no
longer document a partial result on such a page: they throw, as any other request does.

**`SportMonksException`** keeps the `"code - body"` message and adds `getStatusCode()` (e.g.
404, 403, 429; 0 when there is no HTTP response), `getErrorCode()` (the Sportmonks `code` of the
error body, e.g. 5007 for an endpoint outside the subscription) and `getRateLimit()` (the
`rate_limit` of the error body, when present).

```java
try {
    api.getMatchFactsByLeague(8L);
} catch (SportMonksException e) {
    if (e.getStatusCode() == 403 && Integer.valueOf(5007).equals(e.getErrorCode())) { /* no access */ }
}
```

**Path segments are percent-encoded** (#85). Search texts and `String` ids now travel as one
encoded path segment: `searchTeams("Real?x")` searches for `Real?x` (it used to search for
`Real`), and `#`, spaces and accents are encoded too. A `/` is sent as `%2F`, but the API
decodes it before routing, so a search text with a slash still answers 404; the exception now
carries `getStatusCode() == 404`.

**Full list walks ask for 50 rows per page.** Without a `limit`, the first request sends
`per_page=50` (it used to rely on the API default of 25), so a walk takes half the requests.
Cursor requests still never send `per_page`.

**Client options.** New constructors `(apiToken, locale, timezone, ClientOptions)` on
`SportMonksAPI`, `FootballApi`, `OddsApi`, `PredictionsApi` and `CoreApi` accept connect, read and
call timeouts or an `OkHttpClient` of your own; the existing constructors are unchanged. Retries
are opt-in and off by default:

```java
ClientOptions options = ClientOptions.builder()
        .readTimeout(Duration.ofSeconds(20))
        .callTimeout(Duration.ofMinutes(2))
        .maxRetries(3)                       // 429 and 5xx; default 0 (off)
        .maxRetryWait(Duration.ofSeconds(30)) // cap per wait; default 60 s
        .build();
SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY", null, null, options);
```

A 429 is retried after the `rate_limit.resets_in_seconds` of its body only when that wait fits
in `maxRetryWait` (otherwise it throws at once); a 5xx is retried with exponential backoff
(`retryBackoff`, 1 s by default). Other 4xx are never retried.

**Other changes.** A `""` in a `long`/`Long` field is read as `null`, as it already was for
`int`/`Integer`. A `next_cursor` sent as a bare token (the format the changelog documents) is
followed as a cursor, like the URL form the API sends today. `VenueResponse`, `RivalsResponse`
and `CommentsResponse` gain `getPagination()`, and `getSeasonById`/`getBracketsBySeason` return
`null` instead of throwing on an empty response (#82).

## 3.3.0 (unreleased): null safety

`getStandingsCup()` now groups rows with a missing group or a null group name
under the empty-string key (`""`), instead of throwing `NullPointerException`.
This also applies when the `group` include is omitted. Named groups retain their
existing keys, and row order within each group is preserved.

The following getters now declare `org.jetbrains.annotations.Nullable`:

- `League.getCurrentseason()` and `League.getCountry()`
- `Standings.getGroup()`
- `Match.getLeg()` and `Match.getAggregateId()` (also inherited by `MatchDetail`)

This minor release intentionally includes a **source-incompatible change for
Kotlin** as an exception to strict semantic versioning: these properties now have nullable
types, so previously unchecked access must handle null. Java method signatures
and runtime values are unchanged. For example:

```kotlin
val seasonId = league.currentseason?.id
if (seasonId != null) {
    // Load the standings for this season.
}
```

The current season can be null even with `currentSeason` included when no season
is active. Other model getters may still expose platform types; this change is
limited to the getters listed above.

## Migrating to v3.0.0

This guide covers migrating from library version **2.0.3.3** (`SportMonksAPIV3`) to **3.0.0** (`SportMonksAPI`). The underlying Sportmonks API version has not changed (still v3); only the Java library has been restructured.

---

## Executive Summary

| Area | Change |
|---|---|
| Instantiation | `SportMonksAPIV3.getInstance(key)` → `new SportMonksAPI(key)` |
| Method renames | 9 methods renamed (see table below) |
| ID types | Some IDs changed from `String` to `long` |
| Model packages | All imports updated (`v3.model.*` → `football/odds/core.model.*`) |
| Rate limit | Still accessible via `api.getRemainingRequests()` / `api.getMaximumRequests()` |
| New methods | 80+ new endpoints available |

Methods that keep the exact same name and signature: `getMatchDetail`, `getMatchesByDate`, `getMatchesByDateRange`, `getMatchesByDateRangeForTeam`, `getMatchesByMultipleIDs`, `getTodayMatches`, `getTodayMatchesFiltered`, `getLiveMatches`, `getSeasons`, `getMatchOdds`, `getMatchOddsByBookmaker`, `getMatchOddsByMarket`, `getTopScores`, `getTopScoresByStage`, `getTopScoresFiltered`, `getStandings`, `getStandingsCup`, `getVenues`.

---

## Quick Start

**1. Update the Maven dependency**

```xml
<dependency>
    <groupId>es.com.kete1987</groupId>
    <artifactId>sportmonks.library</artifactId>
    <version>3.0.0</version>
</dependency>
```

**2. Replace instantiation**

```java
// Before
import es.com.kete1987.sportmonks.library.SportMonksAPIV3;
SportMonksAPIV3 api = SportMonksAPIV3.getInstance("your_api_key");

// After
import es.com.kete1987.sportmonks.library.SportMonksAPI;
SportMonksAPI api = new SportMonksAPI("your_api_key");
```

No longer a singleton — each instance is independent and manages its own HTTP client and rate-limit state.

**Optional locale:**

```java
SportMonksAPI api = new SportMonksAPI("your_api_key", "en");
```

---

## Renamed Methods

| Old (`SportMonksAPIV3`) | New (`SportMonksAPI`) | Notes |
|---|---|---|
| `getSeasonData(String seasonId)` | `getSeasonById(long id, String... includes)` | ID is now `long`; no default includes |
| `getTeams(String seasonId, String... includes)` | `getTeamsBySeasonId(long seasonId, String... includes)` | ID is now `long` |
| `getTeam(String teamId, String... includes)` | `getTeamById(long id, String... includes)` | ID is now `long` |
| `getLeagues(String... includes)` | `getAllLeagues(String... includes)` | |
| `getLeague(String leagueId, String... includes)` | `getLeagueById(long id, String... includes)` | ID is now `long` |
| `getStages(String seasonId, String... includes)` | `getStagesBySeasonId(long seasonId, String... includes)` | ID is now `long` |
| `getStage(String stageId, String... includes)` | `getStageById(long id, String... includes)` | ID is now `long` |
| `getRounds(String seasonId, String... includes)` | `getRoundsBySeasonId(long seasonId, String... includes)` | ID is now `long` |
| `getRound(String roundId, String... includes)` | `getRoundById(long id, String... includes)` | ID is now `long` |

---

## Default Includes — What Changed

Some methods include related data automatically when called with no explicit includes. The following defaults are **preserved** from the old version:

| Method | Default includes (no-arg overload) |
|---|---|
| `getMatchDetail(String matchId)` | `"venue"`, `"state"`, `"lineups"`, `"events"`, `"statistics"`, `"periods"`, `"participants"`, `"scores"` |
| `getMatchesByDateRange(String from, String to)` | `"participants"` |
| `getTopScores(String seasonId)` | `"season"`, `"stage"`, `"player"`, `"type"`, `"participant"` |
| `getTopScoresByStage(String stageId)` | `"season"`, `"stage"`, `"player"`, `"type"` |

**`getSeasonData` / `getSeasonById` — breaking change:**

`getSeasonData` in the old version automatically added `"stages"` and `"fixtures"` as includes. `getSeasonById` has no default includes. Pass them explicitly:

```java
// Before
SeasonData s = api.getSeasonData("12345");

// After — equivalent
SeasonData s = api.getSeasonById(12345L, "stages", "fixtures");

// After — just the season metadata, no nested data
SeasonData s = api.getSeasonById(12345L);
```

---

## ID Type Changes

In the old version all method parameters were `String`. In v3.0.0, entity IDs use `long`:

**Affected methods:** `getSeasonById`, `getTeamById`, `getTeamsBySeasonId`, `getLeagueById`, `getStageById`, `getStagesBySeasonId`, `getRoundById`, `getRoundsBySeasonId`, and all newly added methods.

**Methods that keep `String` IDs** for backwards compatibility: `getMatchDetail`, `getMatchesByDate`, `getMatchesByDateRange`, `getMatchesByDateRangeForTeam`, `getMatchesByMultipleIDs`, `getMatchOdds`, `getMatchOddsByBookmaker`, `getMatchOddsByMarket`, `getTopScores`, `getTopScoresByStage`, `getTopScoresFiltered`, `getStandings`, `getStandingsCup`, `getVenues`.

```java
// Before
Team t = api.getTeam("676");

// After
Team t = api.getTeamById(676L, "sport", "country", "venue");
```

---

## Model Package Changes

All model classes have moved. Update your imports:

| Old package | New package |
|---|---|
| `...library.v3.model.match.MatchDetail` | `...library.football.model.match.MatchDetail` |
| `...library.v3.model.league.League` | `...library.football.model.league.League` |
| `...library.v3.model.season.SeasonData` | `...library.football.model.season.SeasonData` |
| `...library.v3.model.stage.Stage` | `...library.football.model.stage.Stage` |
| `...library.v3.model.rounds.Round` | `...library.football.model.rounds.Round` |
| `...library.v3.model.standings.Standings` | `...library.football.model.standings.Standings` |
| `...library.v3.model.team.Team` | `...library.football.model.team.Team` |
| `...library.v3.model.topscorers.TopScoresPlayer` | `...library.football.model.topscorers.TopScoresPlayer` |
| `...library.v3.model.venue.Venue` | `...library.football.model.venue.Venue` |
| `...library.v3.model.odds.Odd` | `...library.odds.model.Odd` |
| `...library.v3.model.odds.Bookmaker` | `...library.odds.model.Bookmaker` |

Old prefix: `es.com.kete1987.sportmonks.library.v3.model.*`  
New prefix: `es.com.kete1987.sportmonks.library.{football|odds|core}.model.*`

---

## Rate Limit

Rate limit information is available via the same method names as before:

```java
api.getAllContinents(); // any request first
String remaining = api.getRemainingRequests(); // "499"
String maximum   = api.getMaximumRequests();   // "500"
```

Both return `null` until the first request is made.

---

## New Functionality in v3.0.0

The following endpoint groups did not exist in the previous version:

| Group | Key methods |
|---|---|
| **Core — Continents** | `getAllContinents`, `getContinentById` |
| **Core — Countries** | `getAllCountries`, `getCountryById`, `searchCountries` |
| **Core — Regions** | `getAllRegions`, `getRegionById`, `searchRegions` |
| **Core — Cities** | `getAllCities`, `getCityById`, `searchCities` |
| **Core — Types** | `getAllTypes`, `getTypeById`, `getTypesByEntity` |
| **Core — Timezones** | `getAllTimezones` |
| **Core — Filters** | `getAllEntityFilters` |
| **MySportmonks** | `getMyApi`, `getMyLeagues`, `getMyEnrichments`, `getMyResources`, `getMyUsage` |
| **Squads** | `getSquadByTeam`, `getExtendedSquadByTeam`, `getSquadBySeasonAndTeam` |
| **Players** | `getAllPlayers`, `getPlayerById`, `getPlayersByCountry`, `searchPlayers` |
| **Coaches** | `getAllCoaches`, `getCoachById`, `getCoachesByCountry`, `searchCoaches` |
| **Referees** | `getAllReferees`, `getRefereeById`, `getRefereesByCountry`, `getRefereesBySeason` |
| **Statistics** | `getSeasonStatsByParticipant`, `getStatsByStage`, `getStatsByRound` |
| **Schedules** | `getSchedulesBySeason`, `getSchedulesByTeam`, `getSchedulesBySeasonAndTeam` |
| **Transfers** | `getAllTransfers`, `getLatestTransfers`, `getTransfersByDateRange`, `getTransfersByTeam`, `getTransfersByPlayer` |
| **Transfer Rumours** | `getAllTransferRumours`, `getTransferRumoursByTeam`, `getTransferRumoursByPlayer` |
| **Rivals** | `getAllRivals`, `getRivalsByTeam` |
| **Commentaries** | `getAllCommentaries`, `getCommentaryByFixture` |
| **TV Stations** | `getAllTvStations`, `getTvStationById`, `getTvStationsByFixture` |
| **Match Facts** *(beta)* | `getAllMatchFacts`, `getMatchFactsByFixture`, `getMatchFactsByLeague` |
| **Team of the Week** *(beta)* | `getAllTeamsOfTheWeek`, `getTeamOfTheWeekByRound`, `getLatestTeamOfTheWeekByLeague` |
| **Team Rankings** *(beta)* | `getAllTeamRankings`, `getTeamRankingsByTeam`, `getTeamRankingsByDate` |
| **Expected Lineups** *(beta)* | `getExpectedLineupByTeam`, `getExpectedLineupsByPlayer` |
| **Odds: Markets** | `getAllMarkets`, `getMarketById`, `searchMarkets` |
| **Odds: Bookmakers** | `getAllBookmakers`, `getBookmakerById`, `searchBookmakers`, `getBookmakerMappingsByFixture` |
| **Odds: Inplay** | `getInplayOddsByFixture`, `getInplayOddsByFixtureAndBookmaker`, `getInplayOddsByFixtureAndMarket`, `getLastUpdatedInplayOdds` |
| **Odds: Premium Pre-match** | `getPremiumPreMatchOddsByFixture`, `getPremiumPreMatchOddsByFixtureAndBookmaker`, `getPremiumPreMatchOddsByFixtureAndMarket`, `getLastUpdatedPremiumPreMatchOdds` |
| **Odds: Premium Inplay** | `getPremiumInplayOddsByFixture`, `getPremiumInplayOddsByFixtureAndBookmaker`, `getPremiumInplayOddsByFixtureAndMarket`, `getLastUpdatedPremiumInplayOdds` |
| **Predictions** | `getAllProbabilities`, `getPredictabilityByLeague`, `getProbabilitiesByFixture`, `getAllValueBets`, `getValueBetsByFixture` |
| **Expected Goals (XG)** | `getExpectedGoalsByTeam`, `getExpectedGoalsByPlayer` |
| **News** | `getAllPreMatchNews`, `getPreMatchNewsBySeason`, `getPreMatchNewsForUpcoming`, `getAllPostMatchNews`, `getPostMatchNewsBySeason` |

All new methods are available directly on `SportMonksAPI`. Each group is also available independently via `api.getFootball()`, `api.getOdds()`, `api.getPredictions()`, and `api.getCore()` if you prefer to work with sub-API instances.
