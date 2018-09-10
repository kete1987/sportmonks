package com.kete.sportmonks.library;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kete.sportmonks.library.model.League;
import com.kete.sportmonks.library.model.LeagueData;
import com.kete.sportmonks.library.model.Match;
import com.kete.sportmonks.library.model.MatchData;
import com.kete.sportmonks.library.model.MatchDetail;
import com.kete.sportmonks.library.model.MatchsResponse;
import com.kete.sportmonks.library.model.OddType;
import com.kete.sportmonks.library.model.OddsResponse;
import com.kete.sportmonks.library.model.PlayerData;
import com.kete.sportmonks.library.model.SeasonData;
import com.kete.sportmonks.library.model.SeasonDataResponse;
import com.kete.sportmonks.library.model.StandingTeam;
import com.kete.sportmonks.library.model.StandingsData;
import com.kete.sportmonks.library.model.Team;
import com.kete.sportmonks.library.model.TeamDetail;
import com.kete.sportmonks.library.model.TopScores;
import com.kete.sportmonks.library.model.TopScoresPlayer;
import com.kete.sportmonks.library.model.Venue;
import com.kete.sportmonks.library.model.VenueDetail;
import com.kete.sportmonks.library.net.GetResponse;
import com.kete.sportmonks.library.net.HttpFunctions;
import com.kete.sportmonks.library.util.Constants;
import com.kete.sportmonks.library.util.PlayerDataAdapter;
import com.kete.sportmonks.library.util.SportMonksException;

/**
 * SocceramaAPI Main Class
 * 
 * Singleton class
 *
 */
public class SportMonksAPI {
	private static SportMonksAPI instance;
	// private static Logger logger;
	private String apiKey = null;
	private String baseURL = "https://soccer.sportmonks.com/api/v2.0/";

	private SportMonksAPI(String api) {
		apiKey = api;
	}

	public static SportMonksAPI getInstance(String api) {
		if (instance == null) {
			instance = new SportMonksAPI(api);
			return instance;
		} else
			return instance;
	}

	// REQUESTS
	/**
	 * Get list of matches by date range
	 * 
	 * @param beginDate
	 *            Begin date
	 * @param endDate
	 *            End date
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<Match> getMatchesByDateRange(String beginDate, String endDate, String... includes)
			throws IOException, SportMonksException {
		String url = baseURL + "fixtures/between/" + beginDate + "/" + endDate + "?api_token=" + apiKey;
		if (includes != null && includes.length > 0) {
			url += "&include=" + String.join(",", includes);
		}
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			MatchsResponse matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
			List<Match> matchsList = matchsResponse.getListOfMatches();
			int page = 1;
			int totalPages = matchsResponse.getMetadata().getPagination().getTotalPages();
			while (page < totalPages) {
				page++;
				String urlAux = url + "&page=" + page;
				response = HttpFunctions.get(urlAux);
				if (response.getResponseCode() == Constants.RESPONSE_OK) {
					gson = new Gson();
					matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
					List<Match> matchsListAux = matchsResponse.getListOfMatches();
					for (int i = 0; i < matchsListAux.size(); i++)
						matchsList.add(matchsListAux.get(i));
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
	 * @param includes
	 *            include parameters
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<Match> getTodayMatches(String... includes) throws IOException, SportMonksException {

		String url = baseURL + "livescores" + "?api_token=" + apiKey;

		if (includes != null && includes.length > 0) {
			url += "&include=" + String.join(",", includes);
		}

		GetResponse response = HttpFunctions.get(url);

		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			MatchsResponse matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
			List<Match> matchsList = matchsResponse.getListOfMatches();
			int page = 1;
			int totalPages = matchsResponse.getMetadata().getPagination().getTotalPages();
			while (page < totalPages) {
				page++;
				String urlAux = url + "&page=" + page;
				response = HttpFunctions.get(urlAux);
				if (response.getResponseCode() == Constants.RESPONSE_OK) {
					gson = new Gson();
					matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
					List<Match> matchsListAux = matchsResponse.getListOfMatches();
					for (int i = 0; i < matchsListAux.size(); i++)
						matchsList.add(matchsListAux.get(i));
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
	 * @param includes
	 *            include parameters
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public List<Match> getLiveMatches(String... includes) throws IOException, SportMonksException {
		String url = baseURL + "livescores/now" + "?api_token=" + apiKey;

		if (includes != null && includes.length > 0) {
			url += "&include=" + String.join(",", includes);
		}

		GetResponse response = HttpFunctions.get(url);

		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			MatchsResponse matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
			List<Match> matchsList = matchsResponse.getListOfMatches();
			return matchsList;
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get match detail
	 * 
	 * @param matchId
	 *            Match ID
	 * @return Match Detail
	 * @throws IOException
	 * @throws SportMonksException
	 */
	public MatchDetail getMatchDetail(String matchId) throws IOException, SportMonksException {
		String url = baseURL + "fixtures/" + matchId + "?api_token=" + apiKey
				+ "&include=localTeam,visitorTeam,events.player,lineup.player,stats";
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Type myOtherClassListType = new TypeToken<PlayerData>() {
			}.getType();
			Gson gson = new GsonBuilder().registerTypeAdapter(myOtherClassListType, new PlayerDataAdapter()).create();
			MatchData matchData = gson.fromJson(response.getResponse(), MatchData.class);
			return matchData.getMatchDetail();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get season data
	 * 
	 * @param seasonId
	 *            Season ID
	 * @return Season Data (Results, stages, league)
	 * @throws IOException
	 */
	public List<SeasonData> getSeasonData(String seasonID) throws IOException, SportMonksException {
		String url = baseURL + "seasons/" + seasonID + "?api_token=" + apiKey
				+ "&include=stages.fixtures.localTeam,stages.fixtures.visitorTeam,league,results,groups.fixtures,groups.standings,groups.fixtures.localTeam,groups.fixtures.visitorTeam";
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			SeasonDataResponse seasonResults = gson.fromJson(response.getResponse(), SeasonDataResponse.class);
			return seasonResults.getData();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get a list with different odds (pre-match)
	 * 
	 * @param matchID
	 *            Match ID
	 * @return List with pre-match odds
	 * @throws IOException
	 */
	public List<OddType> getMatchOdds(String matchID) throws IOException, SportMonksException {
		String url = baseURL + "odds/fixture/" + matchID + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			OddsResponse oddsResponse = gson.fromJson(response.getResponse(), OddsResponse.class);
			return oddsResponse.getOddTypes();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get Topscores list
	 * 
	 * @param seasonId
	 *            Season ID
	 * @return List of topscores
	 * @throws IOException
	 */
	public List<TopScoresPlayer> getTopScores(String seasonId) throws IOException, SportMonksException {
		String url = baseURL + "topscorers/season/" + seasonId + "?api_token=" + apiKey
				+ "&include=goalscorers.player,goalscorers.team";
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			TopScores topScoresResponse = gson.fromJson(response.getResponse(), TopScores.class);
			return topScoresResponse.getListOfTopScores();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	/**
	 * Get standings list
	 * 
	 * @param seasonId
	 *            Season ID
	 * @return List of standing teams
	 * @throws IOException
	 */
	public List<StandingTeam> getStandings(String seasonId) throws IOException, SportMonksException {
		String url = baseURL + "standings/season/" + seasonId + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			StandingsData standingsResponse = gson.fromJson(response.getResponse(), StandingsData.class);
			if (standingsResponse.getStandings() != null)
				return standingsResponse.getStandings().getStandingsList();
			else
				return new ArrayList<StandingTeam>();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}

	public List<VenueDetail> getVenues(String seasonId) throws IOException, SportMonksException{
		String url = baseURL + "venues/season/" + seasonId + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			Venue venue = gson.fromJson(response.getResponse(), Venue.class);
			if (venue != null && venue.getData() != null)
				return venue.getData();
			else
				return new ArrayList<VenueDetail>();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}
	
	public List<TeamDetail> getTeams(String seasonId)throws IOException, SportMonksException {
		String url = baseURL + "/teams/season/" + seasonId + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			Team teams = gson.fromJson(response.getResponse(), Team.class);
			if (teams != null && teams.getTeamDetails() != null)
				return teams.getTeamDetails();
			else
				return new ArrayList<TeamDetail>();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}


	public List<SeasonData> getSeasons() throws IOException, SportMonksException {
		String url = baseURL + "seasons"  + "?api_token=" + apiKey;
		
		GetResponse response = HttpFunctions.get(url);
		
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			SeasonDataResponse seasonData = gson.fromJson(response.getResponse(), SeasonDataResponse.class);
			if (seasonData != null &&  seasonData.getData() != null)
				return seasonData.getData();
			else
				return new ArrayList<SeasonData>();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}
	
	public List<League> getLeagues() throws IOException, SportMonksException {
		String url = baseURL + "leagues"  + "?api_token=" + apiKey;
		
		GetResponse response = HttpFunctions.get(url);
		
		if (response.getResponseCode() == Constants.RESPONSE_OK) {
			Gson gson = new Gson();
			LeagueData leagueData = gson.fromJson(response.getResponse(), LeagueData.class);
			if (leagueData != null &&  leagueData.getData() != null)
				return leagueData.getData();
			else
				return new ArrayList<League>();
		} else
			throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}
}
