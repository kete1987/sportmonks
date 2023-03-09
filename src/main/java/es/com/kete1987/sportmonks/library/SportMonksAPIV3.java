package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.com.kete1987.sportmonks.library.common.util.EmptyStringToNumberTypeAdapter;
import es.com.kete1987.sportmonks.library.v3.model.match.MatchData;
import es.com.kete1987.sportmonks.library.v3.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.common.net.GetResponse;
import es.com.kete1987.sportmonks.library.common.net.HttpFunctions;
import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.v3.model.match.MatchsResponse;
import es.com.kete1987.sportmonks.library.v3.model.odds.Odd;
import es.com.kete1987.sportmonks.library.v3.model.odds.OddsResponse;
import es.com.kete1987.sportmonks.library.v3.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.v3.model.season.SeasonDataResponse;
import es.com.kete1987.sportmonks.library.v3.model.standings.Standings;
import es.com.kete1987.sportmonks.library.v3.model.standings.StandingsResponse;
import es.com.kete1987.sportmonks.library.v3.model.topscorers.TopScorersResponse;
import es.com.kete1987.sportmonks.library.v3.model.topscorers.TopScoresPlayer;
import es.com.kete1987.sportmonks.library.v3.model.venue.Venue;
import es.com.kete1987.sportmonks.library.v3.model.venue.VenueResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SocceramaAPI Main Class
 * 
 * Singleton class
 *
 */
public class SportMonksAPIV3
{
	private static SportMonksAPIV3 instance;
	private String apiKey = null;
	private String headerPending = null;
	private String headerTotal = null;

	private SportMonksAPIV3(String api)
	{
		apiKey = api;
	}

	public static SportMonksAPIV3 getInstance(String api) {
		if (instance == null) {
			instance = new SportMonksAPIV3(api);
			return instance;
		}
		else return instance;
	}

	public String getRemainingRequests() {
		return headerPending;
	}

	public String getMaximumRequests() {
		return headerTotal;
	}

    /**
     * Return includes in string format
     *
     * @param includes Includes
     * @return Includes in string format to be attached on URL
     */
	private String getIncludes(String... includes) {
        if (includes != null && includes.length > 0) {
            return "&include=" + String.join(";", includes);
        }
        else {
			return "";
		}
    }

	/**
	 * Update headers (pending requests) obtained in response
	 *
	 * @param response Response
	 */
	private void updateHeaders(GetResponse response) {
		headerPending = response.getHeaderPending();
		headerTotal = response.getHeaderTotal();
	}

	// REQUESTS

	/**
	 * Returns list of matches from URL
	 *
	 * @param url URL to obtain matches
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	private List<MatchDetail> getMatchesByURL(String url) throws IOException, SportMonksException{
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new GsonBuilder()
							.registerTypeAdapter(int.class, new EmptyStringToNumberTypeAdapter())
							.registerTypeAdapter(Integer.class, new EmptyStringToNumberTypeAdapter())
							.create();
			MatchsResponse matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
			List<MatchDetail> matchsList = matchsResponse.getData();
			int page = 1;
			boolean hasMorePages = matchsResponse.getPagination() != null && matchsResponse.getPagination().hasMore();
			while (hasMorePages) {
				page++;
				String urlAux = url + "&page=" + page;
				response = HttpFunctions.get(urlAux);
				updateHeaders(response);
				if (response.getResponseCode() == Constants.RESPONSE_OK) {
					gson = new GsonBuilder()
							.registerTypeAdapter(int.class, new EmptyStringToNumberTypeAdapter())
							.registerTypeAdapter(Integer.class, new EmptyStringToNumberTypeAdapter())
							.create();
					matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
					List<MatchDetail> matchsListAux = matchsResponse.getData();
					matchsList.addAll(matchsListAux);
					hasMorePages = matchsResponse.getPagination() != null && matchsResponse.getPagination().hasMore();
				} else
					throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
			}
			return matchsList;
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get list of today's matches
	 *
	 * @param includes include parameters
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<MatchDetail> getTodayMatches(String... includes) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "livescores" + "?api_token=" + apiKey + getIncludes(includes);
		return getMatchesByURL(url);
	}

//	/**
//	 * Get list of today's matches filtered by a list of match IDs
//	 *
//	 * @param matches list of matches
//	 * @param includes include parameters
//	 * @return List of matches
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public List<MatchDetail> getTodayMatchesFiltered(String[] matches, String... includes) throws IOException, SportMonksException {
//		if (matches != null && matches.length > 0) {
//			String url = Constants.baseURLV3 + "livescores" + "?api_token=" + apiKey + getIncludes(includes) + "&fixtures=" + String.join(",", matches);
//			return getMatchesByURL(url);
//		}
//		else
//			return getTodayMatches(includes);
//	}

	/**
	 * Get list of today's live matches
	 *
	 * @param includes include parameters
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<MatchDetail> getLiveMatches(String... includes) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "livescores/inplay" + "?api_token=" + apiKey + getIncludes(includes);
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			MatchsResponse matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
			return matchsResponse.getData();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get list of  matches for a particular date
	 *
	 * @param includes include parameters
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<MatchDetail> getMatchesByDate(String date, String... includes) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "fixtures/date/" + date + "?api_token=" + apiKey + getIncludes(includes);
		return getMatchesByURL(url);
	}

	/**
	 * Get list of matches by date range
	 *
	 * @param beginDate Begin date
	 * @param endDate End date
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate) throws IOException, SportMonksException {
		String[] includes = {"participants"};
		return getMatchesByDateRange(beginDate, endDate, includes);
	}

	/**
	 * Get list of matches by date range
	 *
	 * @param beginDate Begin date
	 * @param endDate End date
	 * @param includes Includes
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate, String... includes) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "fixtures/between/" + beginDate + "/" + endDate + "?api_token=" + apiKey + getIncludes(includes);
		return getMatchesByURL(url);
	}

//	/**
//	 * Get list of matches by date range restricted by team
//	 *
//	 * @param beginDate Begin date
//	 * @param endDate End date
//	 * @param teamId Team ID
//	 * @param includes Includes
//	 * @return List of matches
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public List<MatchDetail> getMatchesByDateRangeForTeam(String beginDate, String endDate, String teamId, String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "fixtures/between/" + beginDate + "/" + endDate + "/" + teamId + "?api_token=" + apiKey + getIncludes(includes);
//		return getMatchesByURL(url);
//	}
//
//	/**
//	 * Get list of matches by date range restricted by team
//	 *
//	 * @param teamIds List of Team ID
//	 * @param includes Includes
//	 * @return List of matches
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public List<MatchDetail> getMatchesByMultipleIDs(String[] teamIds, String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "fixtures/multi/" + String.join(",", teamIds) + "?api_token=" + apiKey + getIncludes(includes);
//		return getMatchesByURL(url);
//	}

	/**
	 * Get match detail
	 * 
	 * @param matchId Match ID
	 * @return Match Detail
	 * @throws IOException
	 * @throws SportMonksException 
	 */
	public MatchDetail getMatchDetail(String matchId) throws IOException, SportMonksException {
	    String[] includes = {"venue", "state", "lineups", "events", "statistics", "periods", "participants", "scores"};
        return getMatchDetail(matchId, includes);
	}

    /**
     * Get match detail
     *
     * @param matchId Match ID
	 * @param includes Includes
     * @return Match Detail
     * @throws IOException
     * @throws SportMonksException
     */
    public MatchDetail getMatchDetail(String matchId, String... includes) throws IOException, SportMonksException {
        String url = Constants.baseURLV3 + "fixtures/" + matchId + "?api_token=" + apiKey + getIncludes(includes);
        GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
        if (response.getResponseCode() == Constants.RESPONSE_OK) {
//            Type myOtherClassListType = new TypeToken<PlayerData>() {}.getType();
            Gson gson = new GsonBuilder()
//                    .registerTypeAdapter(myOtherClassListType, new PlayerDataAdapter())
					.registerTypeAdapter(int.class, new EmptyStringToNumberTypeAdapter())
					.registerTypeAdapter(Integer.class, new EmptyStringToNumberTypeAdapter())
                    .create();
			MatchData matchData = gson.fromJson(response.getResponse(), MatchData.class);
			return matchData.getMatchDetail();
        }
        else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
    }

//	/**
//	 * Get list of seasons
//	 * @return List of seasons
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public List<SeasonData> getSeasons() throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "seasons" + "?api_token=" + apiKey;
//		GetResponse response = HttpFunctions.get(url);
//		updateHeaders(response);
//		if (response.getResponseCode() == Constants.RESPONSE_OK) {
//			Gson gson = new Gson();
//			SeasonDataList seasonResponse = gson.fromJson(response.getResponse(), SeasonDataList.class);
//			List<SeasonData> seasonDataList = seasonResponse.getListOfSeasons();
//			int page = 1;
//			int totalPages = seasonResponse.getMetadata().getPagination().getTotalPages();
//			while (page < totalPages) {
//				page++;
//				String urlAux = url + "&page=" + page;
//				response = HttpFunctions.get(urlAux);
//				updateHeaders(response);
//				if (response.getResponseCode() == Constants.RESPONSE_OK) {
//					gson = new Gson();
//					seasonResponse = gson.fromJson(response.getResponse(), SeasonDataList.class);
//					List<SeasonData> seasonsListAux = seasonResponse.getListOfSeasons();
//					for (int i = 0; i < seasonsListAux.size(); i++)
//						seasonDataList.add(seasonsListAux.get(i));
//				} else
//					throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//			}
//			return seasonDataList;
//		} else
//			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//	}

	/**
	 * Get season data
	 *
	 * @param seasonID Season ID
	 * @return Season Data (Results, stages, league)
	 * @throws IOException
	 */
	public SeasonData getSeasonData(String seasonID) throws IOException, SportMonksException {
		String[] includes = {"stages", "fixtures"};
		return getSeasonData(seasonID, includes);
	}

	/**
	 * Get season data
	 *
	 * @param seasonID Season ID
	 * @param includes Includes
	 * @return Season Data (Results, stages, league)
	 * @throws IOException
	 */
	public SeasonData getSeasonData(String seasonID, String... includes) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "seasons/" + seasonID + "?api_token=" + apiKey + getIncludes(includes);
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			SeasonDataResponse seasonResults = gson.fromJson(response.getResponse(), SeasonDataResponse.class);
			return seasonResults.getSeasonData();
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get a list with different odds (pre-match)
	 *
	 * @param matchID Match ID
	 * @return List with pre-match odds
	 * @throws IOException
	 */
	public List<Odd> getMatchOdds(String matchID) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "odds/pre-match/fixtures/" + matchID + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			OddsResponse oddsResponse = gson.fromJson(response.getResponse(), OddsResponse.class);
		    return oddsResponse.getData();
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get a list with different odds (pre-match)
	 *
	 * @param matchID      Match ID
	 * @param bookMakerId BookMaker ID
	 * @return List with pre-match odds
	 * @throws IOException
	 */
	public List<Odd> getMatchOdds(String matchID, String bookMakerId) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "odds/pre-match/fixtures/" + matchID + "/bookmakers/" + bookMakerId + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			OddsResponse oddsResponse = gson.fromJson(response.getResponse(), OddsResponse.class);
			return oddsResponse.getData();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get a list with different odds (pre-match)
	 *
	 * @param matchID Match ID
	 * @param market  BookMaker ID
	 * @return List with pre-match odds
	 * @throws IOException
	 */
	public List<Odd> getMatchOddsByMarket(String matchID, String market) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "odds/pre-match/fixtures/" + matchID + "/markets/" + market + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			OddsResponse oddsResponse = gson.fromJson(response.getResponse(), OddsResponse.class);
			return oddsResponse.getData();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get Topscores list
	 *
	 * @param seasonId Season ID
	 * @return List of topscores
	 * @throws IOException
	 */
	public List<TopScoresPlayer> getTopScores(String seasonId) throws IOException, SportMonksException
	{
		String[] includes = {"season", "stage", "player", "type"};
		return getTopScores(seasonId, includes);
	}

	/**
	 * Get Topscores list
	 *
	 * @param seasonId Season ID
	 * @param includes Includes
	 * @return List of topscores
	 * @throws IOException
	 */
	public List<TopScoresPlayer> getTopScores(String seasonId, String... includes) throws IOException, SportMonksException
	{
		String url = Constants.baseURLV3 + "topscorers/seasons/" + seasonId + "?api_token=" + apiKey + getIncludes(includes);
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			TopScorersResponse topScoresResponse = gson.fromJson(response.getResponse(), TopScorersResponse.class);
		    return topScoresResponse.getData();
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get Topscores list
	 *
	 * @param stageId Stage ID
	 * @return List of topscores
	 * @throws IOException
	 */
	public List<TopScoresPlayer> getTopScoresByStage(String stageId) throws IOException, SportMonksException
	{
		String[] includes = {"season", "stage", "player", "type"};
		return getTopScoresByStage(stageId, includes);
	}

	/**
	 * Get Topscores list
	 *
	 * @param stageId stageId
	 * @param includes Includes
	 * @return List of topscores
	 * @throws IOException
	 */
	public List<TopScoresPlayer> getTopScoresByStage(String stageId, String... includes) throws IOException, SportMonksException
	{
		String url = Constants.baseURLV3 + "topscorers/stages/" + stageId + "?api_token=" + apiKey + getIncludes(includes);
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			TopScorersResponse topScoresResponse = gson.fromJson(response.getResponse(), TopScorersResponse.class);
			return topScoresResponse.getData();
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get standings list
	 *
	 * @param seasonId Season ID
	 * @return List of standing teams
	 * @throws IOException
	 */
	public List<Standings> getStandings(String seasonId, String... includes) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "standings/seasons/" + seasonId + "?api_token=" + apiKey + getIncludes(includes);
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			StandingsResponse standingsResponse = gson.fromJson(response.getResponse(), StandingsResponse.class);
		    if (standingsResponse.getData() != null)
		    	return standingsResponse.getData();
		    else
		    	return new ArrayList<>();
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get venue detail
	 *
	 * @param seasonId Season ID
	 * @return Venue detail
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<Venue> getVenues(String seasonId) throws IOException, SportMonksException {
		String url = Constants.baseURLV3 + "venues/seasons/" + seasonId + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		updateHeaders(response);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			VenueResponse venue = gson.fromJson(response.getResponse(), VenueResponse.class);
			if (venue != null && venue.getData() != null)
				return venue.getData();
			else
				return new ArrayList<>();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

//	/**
//	 * Get list of teams
//	 *
//	 * @param seasonId Season ID
//	 * @param includes Includes
//	 * @return List of teams
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public List<TeamDetail> getTeams(String seasonId, String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "teams/season/" + seasonId + "?api_token=" + apiKey;
//		if (includes != null && includes.length > 0) {
//			url += "&include=" + String.join(",", includes);
//		}
//		GetResponse response = HttpFunctions.get(url);
//		updateHeaders(response);
//		if (response.getResponseCode() == Constants.RESPONSE_OK) {
//			Gson gson = new Gson();
//			TeamsResponse teamsResponse = gson.fromJson(response.getResponse(), TeamsResponse.class);
//			if (teamsResponse != null && teamsResponse.getListOfTeams() != null)
//				return teamsResponse.getListOfTeams();
//			else
//				return new ArrayList<>();
//		} else
//			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//	}
//
//	/**
//	 * Get team
//	 *
//	 * @param teamId Team ID
//	 * @param includes Includes
//	 * @return Team info
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public TeamDetail getTeam(String teamId, String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "teams/" + teamId + "?api_token=" + apiKey + getIncludes(includes);
//		GetResponse response = HttpFunctions.get(url);
//		updateHeaders(response);
//		if (response.getResponseCode() == Constants.RESPONSE_OK) {
//			Gson gson = new Gson();
//			Team team = gson.fromJson(response.getResponse(), Team.class);
//			if (team != null && team.getTeamDetail() != null)
//				return team.getTeamDetail();
//			else
//				return new TeamDetail();
//		} else
//			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//	}
//
//	/**
//	 * Get list of leagues
//	 * @return List of leagues
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public List<League> getLeagues(String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "leagues" + "?api_token=" + apiKey + getIncludes(includes);
//		GetResponse response = HttpFunctions.get(url);
//		updateHeaders(response);
//		if (response.getResponseCode() == Constants.RESPONSE_OK) {
//			Gson gson = new Gson();
//			LeagueResponse leagueResponse = gson.fromJson(response.getResponse(), LeagueResponse.class);
//			if (leagueResponse != null && leagueResponse.getListOfLeagues() != null)
//				return leagueResponse.getListOfLeagues();
//			else
//				return new ArrayList<>();
//		} else
//			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//	}
//
//	/**
//	 * Get league by ID
//	 * @param leagueId League ID
//	 * @return League
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public League getLeague(String leagueId, String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "leagues/" + leagueId + "?api_token=" + apiKey + getIncludes(includes);
//		GetResponse response = HttpFunctions.get(url);
//		updateHeaders(response);
//		if (response.getResponseCode() == Constants.RESPONSE_OK) {
//			Gson gson = new Gson();
//			LeagueData leagueData = gson.fromJson(response.getResponse(), LeagueData.class);
//			if (leagueData != null && leagueData.getData() != null)
//				return leagueData.getData();
//			else
//				return null;
//		} else
//			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//	}
//
//	/**
//	 * Return stages list
//	 * @param seasonId Season ID
//	 * @param includes Includes
//	 * @return Stages list
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public List<Stage> getStages(String seasonId, String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "stages/season/" + seasonId + "?api_token=" + apiKey + getIncludes(includes);
//		GetResponse response = HttpFunctions.get(url);
//		updateHeaders(response);
//		if (response.getResponseCode() == Constants.RESPONSE_OK) {
//			Gson gson = new Gson();
//			StagesData stagesData = gson.fromJson(response.getResponse(), StagesData.class);
//			if (stagesData != null && stagesData.getListOfStages() != null)
//				return stagesData.getListOfStages();
//			else
//				return new ArrayList<>();
//		} else
//			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//	}
//
//	/**
//	 * Return rounds list
//	 * @param seasonId Season ID
//	 * @param includes Includes
//	 * @return Rounds list
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public List<Round> getRounds(String seasonId, String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "rounds/season/" + seasonId + "?api_token=" + apiKey + getIncludes(includes);
//		GetResponse response = HttpFunctions.get(url);
//		updateHeaders(response);
//		if (response.getResponseCode() == Constants.RESPONSE_OK) {
//			Gson gson = new Gson();
//			RoundDataList roundsList = gson.fromJson(response.getResponse(), RoundDataList.class);
//			if (roundsList != null && roundsList.getData() != null)
//				return roundsList.getData();
//			else
//				return new ArrayList<>();
//		} else
//			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//	}
//
//	/**
//	 * Return round selected
//	 * @param roundId Season ID
//	 * @param includes Includes
//	 * @return Round selected
//	 * @throws IOException
//	 * @throws SportMonksException
//	 */
//	public Round getRound(String roundId, String... includes) throws IOException, SportMonksException {
//		String url = Constants.baseURLV3 + "rounds/" + roundId + "?api_token=" + apiKey + getIncludes(includes);
//		GetResponse response = HttpFunctions.get(url);
//		updateHeaders(response);
//		if (response.getResponseCode() == Constants.RESPONSE_OK) {
//			Gson gson = new Gson();
//			RoundData roundData = gson.fromJson(response.getResponse(), RoundData.class);
//			if (roundData != null && roundData.getData() != null)
//				return roundData.getData();
//			else
//				return null;
//		} else
//			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
//	}
}
