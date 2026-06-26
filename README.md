# Sportmonks Library

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=kete1987_sportmonks&metric=alert_status)](https://sonarcloud.io/summary/overall?id=kete1987_sportmonks)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=kete1987_sportmonks&metric=coverage)](https://sonarcloud.io/summary/overall?id=kete1987_sportmonks)
[![CI](https://github.com/kete1987/sportmonks/actions/workflows/ci.yml/badge.svg)](https://github.com/kete1987/sportmonks/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/es.com.kete1987/sportmonks.library)](https://central.sonatype.com/artifact/es.com.kete1987/sportmonks.library)

Java library (Java 11+) to access the [Sportmonks](https://sportmonks.com) football data API v3.

## Installation

```xml
<dependency>
    <groupId>es.com.kete1987</groupId>
    <artifactId>sportmonks.library</artifactId>
    <version>3.1.0</version>
</dependency>
```

## Quick Start

```java
SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY");

// Optional: request data in a specific language
SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY", "en");

// Optional: also convert date/time fields to a timezone (default is UTC).
// Use null for locale if you only want to set the timezone.
SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY", "en", "Europe/Amsterdam");
```

The `timezone` is sent as the `timezone` query parameter on every request, so date/time
fields in the response are expressed in that zone. Accepted values are the identifiers
returned by `getAllTimezones()`.

All methods throw `IOException` and `SportMonksException`.

## Rate Limiting

Rate-limit headers are read automatically after each request:

```java
api.getAllContinents();
System.out.println(api.getRemainingRequests()); // "499"
System.out.println(api.getMaximumRequests());   // "500"
```

Both return `null` until the first request is made.

Sportmonks also reports rate limits **per entity** in the response body (Sportmonks scopes the
quota per `requested_entity`). These are captured automatically and accumulated across requests:

```java
api.getAllLeagues();
api.getTeamById(1);

RateLimit last = api.getLastRateLimit();          // the most recent call's rate_limit
System.out.println(last.getRemaining());          // e.g. 2999
System.out.println(last.getRequestedEntity());    // "team"

Map<String, RateLimit> byEntity = api.getRateLimitsByEntity();
System.out.println(byEntity.get("league").getRemaining()); // 2999
System.out.println(byEntity.get("team").getRemaining());   // 2999
```

`getLastRateLimit()` returns `null` and `getRateLimitsByEntity()` is empty until the first request.

## Usage Examples

### Fixtures

```java
// Today's matches
List<MatchDetail> today = api.getTodayMatches();

// Matches on a specific date
List<MatchDetail> matches = api.getMatchesByDate("2024-03-15", "participants", "scores");

// Date range
List<MatchDetail> range = api.getMatchesByDateRange("2024-03-13", "2024-03-14");

// Match detail with includes (default includes: venue, state, lineups, events,
// statistics, periods, participants, scores)
MatchDetail match = api.getMatchDetail("18545372");
MatchDetail match = api.getMatchDetail("18545372", "state", "participants", "scores");

// Live
List<MatchDetail> live = api.getLiveMatches("participants", "scores");
```

### Leagues & Seasons

```java
List<League>   leagues = api.getAllLeagues();
League         league  = api.getLeagueById(564L);
List<SeasonData> seasons = api.getSeasons();
SeasonData       season  = api.getSeasonById(13133L, "stages", "fixtures");
```

### Teams & Squads

```java
List<Team> teams = api.getTeamsBySeasonId(13133L);
Team       team  = api.getTeamById(676L, "sport", "country", "venue");
List<Team> found = api.searchTeams("Barcelona");

List<SquadPlayer> squad    = api.getSquadByTeam(676L, "player");
List<SquadPlayer> extended = api.getExtendedSquadByTeam(676L);
```

### Players, Coaches & Referees

```java
Player player = api.getPlayerById(14L, "statistics", "position");
Coach  coach  = api.getCoachById(1L, "nationality");
List<Referee> referees = api.getRefereesBySeason(13133L);
```

### Standings

```java
List<Standings> table    = api.getStandings("13133");
TreeMap<String, List<Standings>> cup = api.getStandingsCup("13133");
List<Standings> live     = api.getLiveStandingsByLeague(564L);
```

### Stages & Rounds

```java
List<Stage> stages = api.getStagesBySeasonId(12950L);
Stage       stage  = api.getStageById(77456L);
List<Round> rounds = api.getRoundsBySeasonId(13133L);
Round       round  = api.getRoundById(151251L);
```

### Top Scorers

```java
// Default includes: season, stage, player, type, participant
List<TopScoresPlayer> scorers = api.getTopScores("13133");
List<TopScoresPlayer> goals   = api.getTopScoresFiltered("13133", 52); // typeId 52 = goals
```

### Odds

```java
// Pre-match odds
List<Odd> odds       = api.getMatchOdds("19439629");
List<Odd> byMarket   = api.getMatchOddsByMarket("19439629", "57");
List<Odd> byBookmaker = api.getMatchOddsByBookmaker("19439629", "2");

// Markets and bookmakers
List<Market>    markets   = api.getAllMarkets();
List<Bookmaker> bookmakers = api.getAllBookmakers();

// Inplay
List<Odd> inplay = api.getInplayOddsByFixture(19439629L);

// Premium feeds
List<Odd> premium = api.getPremiumPreMatchOddsByFixture(19439629L);
```

### Predictions & Value Bets

```java
List<Probability>    probs  = api.getProbabilitiesByFixture(19439629L);
List<ValueBet>       bets   = api.getValueBetsByFixture(19439629L);
List<Predictability> pred   = api.getPredictabilityByLeague(564L);
```

### Expected Goals (XG)

```java
List<ExpectedGoal> xgTeam   = api.getExpectedGoalsByTeam();
List<ExpectedGoal> xgPlayer = api.getExpectedGoalsByPlayer();
```

### Statistics & Schedules

```java
List<Statistic> stats    = api.getSeasonStatsByParticipant(676L);
List<Schedule>  schedule = api.getSchedulesBySeason(13133L);
```

### Transfers

```java
List<Transfer>       latest   = api.getLatestTransfers("player", "toTeam", "fromTeam");
List<Transfer>       byTeam   = api.getTransfersByTeam(676L);
List<TransferRumour> rumours  = api.getTransferRumoursByTeam(676L);
```

### Venues, TV Stations & Rivals

```java
List<Venue>     venues  = api.getVenues("13133");
List<TvStation> tv      = api.getTvStationsByFixture(19439629L);
List<Rival>     rivals  = api.getRivalsByTeam(676L);
```

### News

```java
List<NewsArticle> preMatch  = api.getAllPreMatchNews();
List<NewsArticle> postMatch = api.getAllPostMatchNews();
List<NewsArticle> upcoming  = api.getPreMatchNewsForUpcoming();
```

### Core — Geography

```java
List<Continent> continents = api.getAllContinents();
List<Country>   countries  = api.getAllCountries();
List<Country>   found      = api.searchCountries("Spain");
List<Region>    regions    = api.getAllRegions();
List<City>      cities     = api.searchCities("Madrid");
```

### MySportmonks

```java
MyApi           account = api.getMyApi();
List<MyLeague>  leagues = api.getMyLeagues();
List<MyUsage>   usage   = api.getMyUsage();
```

## Advanced: Sub-API Access

`SportMonksAPI` is a facade. Each domain is also available as an independent class for cases where you only need one area:

```java
FootballApi    football    = new FootballApi("YOUR_API_KEY");
OddsApi        odds        = new OddsApi("YOUR_API_KEY");
PredictionsApi predictions = new PredictionsApi("YOUR_API_KEY");
CoreApi        core        = new CoreApi("YOUR_API_KEY");

// Or via the facade:
football = api.getFootball();
odds     = api.getOdds();
```

## Full Method Reference

### Fixtures / Livescores
`getTodayMatches` · `getTodayMatchesFiltered` · `getLiveMatches` · `getLatestUpdatedLivescores` ·
`getMatchesByDate` · `getMatchesByDateRange` · `getMatchesByDateRangeForTeam` · `getMatchesByMultipleIDs` ·
`getFixturesByHeadToHead` · `searchFixtures` · `getLatestUpdatedFixtures` ·
`getUpcomingFixturesByMarket` · `getUpcomingFixturesByTvStation` · `getPastFixturesByTvStation` · `getMatchDetail`

### Leagues
`getAllLeagues` · `getLeagueById` · `getLiveLeagues` · `getLeaguesByDate` · `getLeaguesByCountry` ·
`searchLeagues` · `getLeaguesByTeam` · `getCurrentLeaguesByTeam`

### Seasons
`getSeasons` · `getSeasonById` · `getSeasonsByTeam` · `searchSeasons` · `getBracketsBySeason`

### Stages
`getAllStages` · `getStagesBySeasonId` · `getStageById` · `searchStages`

### Rounds
`getAllRounds` · `getRoundsBySeasonId` · `getRoundById` · `searchRounds`

### States
`getAllStates` · `getStateById`

### Teams
`getAllTeams` · `getTeamsBySeasonId` · `getTeamsByCountry` · `getTeamById` · `searchTeams`

### Squads
`getSquadByTeam` · `getExtendedSquadByTeam` · `getSquadBySeasonAndTeam`

### Players
`getAllPlayers` · `getPlayerById` · `getPlayersByCountry` · `searchPlayers` · `getLatestUpdatedPlayers`

### Coaches
`getAllCoaches` · `getCoachById` · `getCoachesByCountry` · `searchCoaches` · `getLatestUpdatedCoaches`

### Referees
`getAllReferees` · `getRefereeById` · `getRefereesByCountry` · `getRefereesBySeason` · `searchReferees`

### Standings
`getStandings` · `getStandingsCup` · `getAllStandings` · `getStandingsByRound` ·
`getStandingCorrectionsBySeason` · `getLiveStandingsByLeague` · `getGroupedStandingsByRound`

### Top Scorers
`getTopScores` · `getTopScoresFiltered` · `getTopScoresByStage`

### Statistics
`getSeasonStatsByParticipant` · `getStatsByStage` · `getStatsByRound`

### Schedules
`getSchedulesBySeason` · `getSchedulesByTeam` · `getSchedulesBySeasonAndTeam`

### Transfers
`getAllTransfers` · `getTransferById` · `getLatestTransfers` · `getTransfersByDateRange` ·
`getTransfersByTeam` · `getTransfersByPlayer`

### Transfer Rumours
`getAllTransferRumours` · `getTransferRumourById` · `getTransferRumoursByDateRange` ·
`getTransferRumoursByTeam` · `getTransferRumoursByPlayer`

### Rivals
`getAllRivals` · `getRivalsByTeam`

### Commentaries
`getAllCommentaries` · `getCommentaryByFixture`

### TV Stations
`getAllTvStations` · `getTvStationById` · `getTvStationsByFixture`

### Venues
`getVenues` · `getAllVenues` · `getVenueById` · `searchVenues`

### Match Facts *(beta)*
`getAllMatchFacts` · `getMatchFactsByFixture` · `getMatchFactsByDateRange` · `getMatchFactsByLeague`

### Team of the Week *(beta)*
`getAllTeamsOfTheWeek` · `getTeamOfTheWeekByRound` · `getLatestTeamOfTheWeekByLeague`

### Team Rankings *(beta)*
`getAllTeamRankings` · `getTeamRankingsByTeam` · `getTeamRankingsByDate`

### Expected Lineups *(beta)*
`getExpectedLineupByTeam` · `getExpectedLineupsByPlayer`

### Odds: Pre-match
`getMatchOdds` · `getMatchOddsByMarket` · `getMatchOddsByBookmaker`

### Odds: Markets
`getAllMarkets` · `getMarketById` · `searchMarkets`

### Odds: Bookmakers
`getAllBookmakers` · `getBookmakerById` · `searchBookmakers` · `getBookmakerMappingsByFixture`

### Odds: Inplay
`getInplayOddsByFixture` · `getInplayOddsByFixtureAndBookmaker` · `getInplayOddsByFixtureAndMarket` ·
`getLastUpdatedInplayOdds`

### Odds: Premium Pre-match
`getPremiumPreMatchOddsByFixture` · `getPremiumPreMatchOddsByFixtureAndBookmaker` ·
`getPremiumPreMatchOddsByFixtureAndMarket` · `getLastUpdatedPremiumPreMatchOdds`

### Odds: Premium Inplay
`getPremiumInplayOddsByFixture` · `getPremiumInplayOddsByFixtureAndBookmaker` ·
`getPremiumInplayOddsByFixtureAndMarket` · `getLastUpdatedPremiumInplayOdds`

### Predictions
`getAllProbabilities` · `getPredictabilityByLeague` · `getProbabilitiesByFixture` ·
`getAllValueBets` · `getValueBetsByFixture`

### Expected Goals (XG)
`getExpectedGoalsByTeam` · `getExpectedGoalsByPlayer`

### News
`getAllPreMatchNews` · `getPreMatchNewsBySeason` · `getPreMatchNewsForUpcoming` ·
`getAllPostMatchNews` · `getPostMatchNewsBySeason`

### Core — Geography
`getAllContinents` · `getContinentById` · `getAllCountries` · `getCountryById` · `searchCountries` ·
`getAllRegions` · `getRegionById` · `searchRegions` · `getAllCities` · `getCityById` · `searchCities`

### Core — Metadata
`getAllTypes` · `getTypeById` · `getTypesByEntity` · `getAllTimezones` · `getAllEntityFilters`

### MySportmonks
`getMyApi` · `getMyLeagues` · `getMyResources` · `getMyEnrichments` · `getMyUsage`

---

See the [Sportmonks API documentation](https://docs.sportmonks.com/football) for details on includes and parameters.

## Upgrading from a previous version

See [MIGRATION.md](MIGRATION.md) for a full list of breaking changes, renamed methods, and model package updates.

## Authors
Iván Morales
