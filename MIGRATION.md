# Migration Guide — v3.0.0

This guide covers migrating from the previous `SportMonksAPIV3` (the `master` branch) to `SportMonksAPI` v3.0.0. The underlying Sportmonks API version has not changed (still v3); only the Java library has been restructured.

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
