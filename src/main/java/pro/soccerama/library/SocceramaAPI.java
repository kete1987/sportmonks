package pro.soccerama.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pro.soccerama.library.model.Match;
import pro.soccerama.library.model.MatchData;
import pro.soccerama.library.model.MatchDetail;
import pro.soccerama.library.model.MatchsResponse;
import pro.soccerama.library.model.OddType;
import pro.soccerama.library.model.OddsResponse;
import pro.soccerama.library.model.PlayerData;
import pro.soccerama.library.model.SeasonData;
import pro.soccerama.library.model.SeasonDataResponse;
import pro.soccerama.library.model.StandingTeam;
import pro.soccerama.library.model.StandingsData;
import pro.soccerama.library.model.TopScoresPlayer;
import pro.soccerama.library.model.TopScores;
import pro.soccerama.library.net.GetResponse;
import pro.soccerama.library.net.HttpFunctions;
import pro.soccerama.library.util.Constants;
import pro.soccerama.library.util.PlayerDataAdapter;
import pro.soccerama.library.util.SportMonksException;

/**
 * SocceramaAPI Main Class
 * 
 * Singleton class
 *
 */
public class SocceramaAPI 
{
	private static SocceramaAPI instance;
	//private static Logger logger;
	private String apiKey = null;
	private String baseURL = "https://soccer.sportmonks.com/api/v2.0/";
	
	private SocceramaAPI(String api)
	{
		apiKey = api;
	}
	public static SocceramaAPI getInstance(String api)
	{
		if (instance == null)
		{
			instance = new SocceramaAPI(api);
			return instance;
		}
		else return instance;
	}
	// REQUESTS
	/**
	 * Get list of matches by date range
	 * 
	 * @param beginDate Begin date
	 * @param endDate End date
	 * @return List of matches
	 * @throws IOException
	 * @throws SportMonksException 
	 */
	public List<Match> getMatchesByDateRange(String beginDate, String endDate) throws IOException, SportMonksException
	{
		String url = baseURL + "fixtures/between/" + beginDate + "/" + endDate + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK)
		{
			Gson gson = new Gson();
		    MatchsResponse matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
		    List<Match> matchsList = matchsResponse.getListOfMatches();
		    int page = 1; int totalPages = matchsResponse.getMetadata().getPagination().getTotalPages();
		    while (page < totalPages)
		    {
		    	page++;
		    	String urlAux = url + "&page=" + page;
		    	response = HttpFunctions.get(urlAux);
		    	if (response.getResponseCode() == Constants.RESPONSE_OK)
				{
					gson = new Gson();
				    matchsResponse = gson.fromJson(response.getResponse(), MatchsResponse.class);
				    List<Match> matchsListAux = matchsResponse.getListOfMatches();
				    for (int i=0; i<matchsListAux.size(); i++)
				    	matchsList.add(matchsListAux.get(i));
				}
		    	else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
		    }
		    return matchsList;
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());
	}
	/**
	 * Get match detail
	 * 
	 * @param matchId Match ID
	 * @return Match Detail
	 * @throws IOException
	 * @throws SportMonksException 
	 */
	public MatchDetail getMatchDetail(String matchId) throws IOException, SportMonksException
	{
		String url = baseURL + "fixtures/" + matchId + "?api_token=" + apiKey + "&include=localTeam,visitorTeam,events.player,lineup.player,stats";
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK)
		{
			Type myOtherClassListType = new TypeToken<PlayerData>() {}.getType();
			Gson gson = new GsonBuilder()
			        .registerTypeAdapter(myOtherClassListType, new PlayerDataAdapter())
			        .create();
			MatchData matchData = gson.fromJson(response.getResponse(), MatchData.class);
		    return matchData.getMatchDetail();
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());		
	}
	/**
	 * Get season data
	 * 
	 * @param seasonId Season ID
	 * @return Season Data (Results, stages, league)
	 * @throws IOException
	 */
	public SeasonData getSeasonData(String seasonID) throws IOException, SportMonksException
	{
		String url = baseURL + "seasons/" + seasonID + "?api_token=" + apiKey + "&include=stages.fixtures.localTeam,stages.fixtures.visitorTeam,league,results,groups.fixtures,groups.standings,groups.fixtures.localTeam,groups.fixtures.visitorTeam";
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK)
		{
			Gson gson = new Gson();
			SeasonDataResponse seasonResults = gson.fromJson(response.getResponse(), SeasonDataResponse.class);
		    return seasonResults.getData();
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
	public List<OddType> getMatchOdds(String matchID) throws IOException, SportMonksException
	{
		String url = baseURL + "odds/fixture/" + matchID + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK)
		{
			Gson gson = new Gson();
			OddsResponse oddsResponse = gson.fromJson(response.getResponse(), OddsResponse.class);
		    return oddsResponse.getOddTypes();
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());	
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
		String url = baseURL + "topscorers/season/" + seasonId + "?api_token=" + apiKey + "&include=goalscorers.player,goalscorers.team";
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK)
		{
			Gson gson = new Gson();
		    TopScores topScoresResponse = gson.fromJson(response.getResponse(), TopScores.class);
		    return topScoresResponse.getListOfTopScores();
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
	public List<StandingTeam> getStandings(String seasonId) throws IOException, SportMonksException
	{
		String url = baseURL + "standings/season/" + seasonId + "?api_token=" + apiKey;
		GetResponse response = HttpFunctions.get(url);
		if (response.getResponseCode() == Constants.RESPONSE_OK)
		{
			Gson gson = new Gson();
		    StandingsData standingsResponse = gson.fromJson(response.getResponse(), StandingsData.class);
		    if (standingsResponse.getStandings() != null)
		    	return standingsResponse.getStandings().getStandingsList();
		    else return new ArrayList<StandingTeam>();
		}
		else throw new SportMonksException(response.getResponseCode() + " - " + response.getResponse());			
	}
}
