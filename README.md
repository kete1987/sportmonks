# Sportmonks
Custom library to access sportmonks API

## Table of contents
* [Usage](#usage)
* [Sportmonks API support](#sportmonks-api-support)
* [Add library in your project](#add-library-in-your-project)

## Usage
You only need obtain the SportmonksAPI instance and invoke requested method.

Example: Request all leagues
`SportMonksAPI.getInstance(SPORTMONKS_API_KEY).getLeagues();`

## Sportmonks API support
The available methods are:
* getTodayMatches
* getLiveMatches
* getMatchesByDate
* getMatchesByDateRange
* getMatchesByDateRangeForTeam
* getMatchesByMultipleIDs
* getMatchDetail
* getSeasons
* getSeasonData
* getMatchOdds
* getMatchOddsByMarket
* getTopScores
* getAggregatedTopScores
* getStandings
* getCupStandings
* getVenues
* getTeams
* getLeagues
* getStages

See [sportmonks documentacion](https://sportmonks.com/docs/football/2.0/prologue/a/introduction/94) for details

## Add library in your project

### Add dependency
```xml
<dependency>
    <groupId>es.com.kete1987</groupId>
    <artifactId>sportmonks.library</artifactId>
    <version>2.0.0-SNAPSHOT</version>
</dependency>
```

## Authors
Iván Morales