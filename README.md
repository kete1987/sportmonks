# Sportmonks Library

Java library to access the [Sportmonks](https://sportmonks.com) football data API. Supports both API v2 and v3.

## Add to your project

```xml
<dependency>
    <groupId>es.com.kete1987</groupId>
    <artifactId>sportmonks.library</artifactId>
    <version>2.0.3.3</version>
</dependency>
```

## Usage

Get the singleton instance with your API key and call the desired method:

```java
// API v3 (recommended)
SportMonksAPIV3 api = SportMonksAPIV3.getInstance("YOUR_API_KEY");

// API v2 (legacy)
SportMonksAPIV2 api = SportMonksAPIV2.getInstance("YOUR_API_KEY");
```

All methods throw `IOException` and `SportMonksException`. Check remaining rate-limit quota after any call:

```java
api.getRemainingRequests();
api.getMaximumRequests();
```

## Available methods

Both `SportMonksAPIV2` and `SportMonksAPIV3` expose the same set of methods unless noted.

### Matches
* `getTodayMatches(includes...)`
* `getTodayMatchesFiltered(matchIds, includes...)`
* `getLiveMatches(includes...)`
* `getMatchesByDate(date, includes...)`
* `getMatchesByDateRange(beginDate, endDate, includes...)`
* `getMatchesByDateRangeForTeam(beginDate, endDate, teamId, includes...)`
* `getMatchesByMultipleIDs(ids, includes...)`
* `getMatchDetail(matchId, includes...)`

### Seasons & Rounds
* `getSeasons()`
* `getSeasonData(seasonId, includes...)`
* `getStages(seasonId, includes...)`
* `getStage(stageId, includes...)` *(v3 only)*
* `getRounds(seasonId, includes...)`
* `getRound(roundId, includes...)`

### Teams & Leagues
* `getTeams(seasonId, includes...)`
* `getTeam(teamId, includes...)`
* `getLeagues(includes...)`
* `getLeague(leagueId, includes...)`

### Standings
* `getStandings(seasonId, includes...)`
* `getCupStandings(seasonId)` *(v2)* / `getStandingsCup(seasonId, includes...)` *(v3)*

### Odds
* `getMatchOdds(matchId, includes...)`
* `getMatchOddsByMarket(matchId, market, includes...)`
* `getMatchOddsByBookmaker(matchId, bookmakerId)` *(v3 only)*

### Top Scorers
* `getTopScores(seasonId, includes...)`
* `getAggregatedTopScores(seasonId, includes...)` *(v2 only)*
* `getTopScoresFiltered(seasonId, typeId, includes...)` *(v3 only)*
* `getTopScoresByStage(stageId, includes...)` *(v3 only)*

### Other
* `getVenues(seasonId)`

See the [Sportmonks API documentation](https://docs.sportmonks.com/football) for details on includes and parameters.

## Authors
Iván Morales
