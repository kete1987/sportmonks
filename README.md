# Sportmonks Library

Java library (Java 11+) to access the [Sportmonks](https://sportmonks.com) football data API v3.

## Add to your project

```xml
<dependency>
    <groupId>es.com.kete1987</groupId>
    <artifactId>sportmonks.library</artifactId>
    <version>3.0.0</version>
</dependency>
```

## Usage

```java
SportMonksAPI api = new SportMonksAPI("YOUR_API_KEY");
```

All methods throw `IOException` and `SportMonksException`. Check rate-limit quota after any call:

```java
api.getRemainingRequests();
api.getMaximumRequests();
```

## Available methods

### Fixtures / Livescores
* `getTodayMatches(includes...)`
* `getTodayMatchesFiltered(matchIds, includes...)`
* `getLiveMatches(includes...)`
* `getLatestUpdatedLivescores(includes...)`
* `getMatchesByDate(date, includes...)`
* `getMatchesByDateRange(from, to, includes...)`
* `getMatchesByDateRangeForTeam(from, to, teamId, includes...)`
* `getMatchesByMultipleIDs(ids, includes...)`
* `getFixturesByHeadToHead(team1Id, team2Id, includes...)`
* `searchFixtures(name, includes...)`
* `getLatestUpdatedFixtures(includes...)`
* `getUpcomingFixturesByMarket(marketId, includes...)`
* `getUpcomingFixturesByTvStation(tvStationId, includes...)`
* `getPastFixturesByTvStation(tvStationId, includes...)`
* `getMatchDetail(matchId, includes...)`

### Leagues
* `getAllLeagues(includes...)`
* `getLeagueById(leagueId, includes...)`

### Seasons
* `getAllSeasons(includes...)`
* `getSeasonById(seasonId, includes...)`

### Stages
* `getStagesBySeasonId(seasonId, includes...)`
* `getStageById(stageId, includes...)`

### Rounds
* `getRoundsBySeasonId(seasonId, includes...)`
* `getRoundById(roundId, includes...)`

### Teams
* `getTeamsBySeasonId(seasonId, includes...)`
* `getTeamById(teamId, includes...)`

### Standings
* `getStandings(seasonId, includes...)`
* `getStandingsCup(seasonId, includes...)`

### Top Scorers
* `getTopScores(seasonId, includes...)`
* `getTopScoresFiltered(seasonId, typeId, includes...)`
* `getTopScoresByStage(stageId, includes...)`

### Venues
* `getVenues(seasonId)`

### Odds
* `getMatchOdds(matchId, includes...)`
* `getMatchOddsByMarket(matchId, marketId)`
* `getMatchOddsByBookmaker(matchId, bookmakerId)`

### Core — Geography
* `getAllContinents(includes...)`
* `getContinentById(id, includes...)`
* `getAllCountries(includes...)`
* `getCountryById(id, includes...)`
* `searchCountries(name, includes...)`
* `getAllRegions(includes...)`
* `getRegionById(id, includes...)`
* `searchRegions(name, includes...)`
* `getAllCities(includes...)`
* `getCityById(id, includes...)`
* `searchCities(name, includes...)`

### Core — Metadata
* `getAllTypes(includes...)`
* `getTypeById(id)`
* `getTypesByEntity(entity)`
* `getAllTimezones()`
* `getAllEntityFilters()`

### My Sportmonks
* `getMyApi()`
* `getMyLeagues(includes...)`
* `getMyResources()`
* `getMyEnrichments()`
* `getMyUsage()`

See the [Sportmonks API documentation](https://docs.sportmonks.com/football) for details on includes and parameters.

## Upgrading from a previous version

See [MIGRATION.md](MIGRATION.md) for a full list of breaking changes, renamed methods, and model package updates.

## Authors
Iván Morales
