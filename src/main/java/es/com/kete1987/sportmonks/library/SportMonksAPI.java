package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.com.kete1987.sportmonks.library.common.model.subscription.SubscriptionMeta;
import es.com.kete1987.sportmonks.library.common.model.subscription.SubscriptionMetaDeserializer;
import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.EmptyStringToNumberTypeAdapter;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.city.CitiesResponse;
import es.com.kete1987.sportmonks.library.core.model.city.City;
import es.com.kete1987.sportmonks.library.core.model.city.CityResponse;
import es.com.kete1987.sportmonks.library.core.model.continent.Continent;
import es.com.kete1987.sportmonks.library.core.model.continent.ContinentResponse;
import es.com.kete1987.sportmonks.library.core.model.continent.ContinentsResponse;
import es.com.kete1987.sportmonks.library.core.model.country.CountriesResponse;
import es.com.kete1987.sportmonks.library.core.model.country.Country;
import es.com.kete1987.sportmonks.library.core.model.country.CountryResponse;
import es.com.kete1987.sportmonks.library.core.model.filter.FiltersResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyApi;
import es.com.kete1987.sportmonks.library.core.model.my.MyApiResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyEnrichmentsResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeague;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeaguesResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyResource;
import es.com.kete1987.sportmonks.library.core.model.my.MyResourcesResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsage;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsagesResponse;
import es.com.kete1987.sportmonks.library.core.model.region.Region;
import es.com.kete1987.sportmonks.library.core.model.region.RegionResponse;
import es.com.kete1987.sportmonks.library.core.model.region.RegionsResponse;
import es.com.kete1987.sportmonks.library.core.model.timezone.TimezonesResponse;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.core.model.type.TypeResponse;
import es.com.kete1987.sportmonks.library.core.model.type.TypesResponse;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.league.LeagueResponse;
import es.com.kete1987.sportmonks.library.football.model.league.LeaguesResponse;
import es.com.kete1987.sportmonks.library.football.model.match.MatchData;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.match.MatchsResponse;
import es.com.kete1987.sportmonks.library.football.model.match.State;
import es.com.kete1987.sportmonks.library.football.model.match.StateResponse;
import es.com.kete1987.sportmonks.library.football.model.match.StatesResponse;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.rounds.RoundResponse;
import es.com.kete1987.sportmonks.library.football.model.rounds.RoundsResponse;
import es.com.kete1987.sportmonks.library.football.model.season.Bracket;
import es.com.kete1987.sportmonks.library.football.model.season.BracketsResponse;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonDataResponse;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonsResponse;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.model.stage.StageResponse;
import es.com.kete1987.sportmonks.library.football.model.stage.StagesResponse;
import es.com.kete1987.sportmonks.library.football.model.standings.Standings;
import es.com.kete1987.sportmonks.library.football.model.standings.StandingsResponse;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.team.TeamsResponse;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScoresPlayer;
import es.com.kete1987.sportmonks.library.football.model.topscorers.TopScorersResponse;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import es.com.kete1987.sportmonks.library.football.model.venue.VenueResponse;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import es.com.kete1987.sportmonks.library.odds.model.OddsResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class SportMonksAPI {

    private final OkHttpClient httpClient;
    private final String footballBase;
    private final String oddsBase;
    private final String coreBase;
    private final String myBase;
    private volatile String rateLimitTotal;
    private volatile String rateLimitRemaining;

    public SportMonksAPI(String apiToken) {
        this(new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Authorization", apiToken)
                                .build()))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build(),
                Constants.baseURLFootball,
                Constants.baseURLOdds,
                Constants.baseURLCore,
                Constants.baseURLMy);
    }

    SportMonksAPI(OkHttpClient client, String footballBase, String oddsBase, String coreBase, String myBase) {
        this.httpClient = client;
        this.footballBase = footballBase;
        this.oddsBase = oddsBase;
        this.coreBase = coreBase;
        this.myBase = myBase;
    }

    public String getRemainingRequests() {
        return rateLimitRemaining;
    }

    public String getMaximumRequests() {
        return rateLimitTotal;
    }

    // -------------------------------------------------------------------------
    // Internal helpers
    // -------------------------------------------------------------------------

    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new EmptyStringToNumberTypeAdapter())
                .registerTypeAdapter(Integer.class, new EmptyStringToNumberTypeAdapter())
                .registerTypeAdapter(SubscriptionMeta.class, new SubscriptionMetaDeserializer())
                .create();
    }

    private String execute(HttpUrl url) throws IOException, SportMonksException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = httpClient.newCall(request).execute()) {
            rateLimitTotal = response.header("X-RateLimit-Limit");
            rateLimitRemaining = response.header("X-RateLimit-Remaining");
            String body = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                throw new SportMonksException(response.code() + " - " + body);
            }
            return body;
        }
    }

    private HttpUrl.Builder footballUrl(String path) {
        return HttpUrl.parse(footballBase + path).newBuilder();
    }

    private HttpUrl.Builder oddsUrl(String path) {
        return HttpUrl.parse(oddsBase + path).newBuilder();
    }

    private HttpUrl.Builder coreUrl(String path) {
        return HttpUrl.parse(coreBase + path).newBuilder();
    }

    private HttpUrl.Builder myUrl(String path) {
        return HttpUrl.parse(myBase + path).newBuilder();
    }

    private HttpUrl.Builder withIncludes(HttpUrl.Builder builder, String... includes) {
        if (includes != null && includes.length > 0) {
            builder.addQueryParameter("include", String.join(";", includes));
        }
        return builder;
    }

    // -------------------------------------------------------------------------
    // Paginated fetch helpers
    // -------------------------------------------------------------------------

    private List<MatchDetail> fetchMatchList(HttpUrl url) throws IOException, SportMonksException {
        Gson g = gson();
        String body = execute(url);
        MatchsResponse resp = g.fromJson(body, MatchsResponse.class);
        List<MatchDetail> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, MatchsResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    private List<TopScoresPlayer> fetchTopScoresList(HttpUrl url) throws IOException, SportMonksException {
        Gson g = gson();
        String body = execute(url);
        TopScorersResponse resp = g.fromJson(body, TopScorersResponse.class);
        List<TopScoresPlayer> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, TopScorersResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    // -------------------------------------------------------------------------
    // Matches / Fixtures
    // -------------------------------------------------------------------------

    public List<MatchDetail> getTodayMatches(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("livescores"), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getTodayMatchesFiltered(String[] matchIds, String... includes) throws IOException, SportMonksException {
        if (matchIds != null && matchIds.length > 0) {
            HttpUrl url = withIncludes(footballUrl("livescores/multi/" + String.join(",", matchIds)), includes).build();
            return fetchMatchList(url);
        }
        return getTodayMatches(includes);
    }

    public List<MatchDetail> getLiveMatches(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("livescores/inplay"), includes).build();
        Gson g = gson();
        MatchsResponse resp = g.fromJson(execute(url), MatchsResponse.class);
        return resp.getData();
    }

    public List<MatchDetail> getMatchesByDate(String date, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/date/" + date), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate) throws IOException, SportMonksException {
        return getMatchesByDateRange(beginDate, endDate, "participants");
    }

    public List<MatchDetail> getMatchesByDateRange(String beginDate, String endDate, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/between/" + beginDate + "/" + endDate), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByDateRangeForTeam(String beginDate, String endDate, String teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/between/" + beginDate + "/" + endDate + "/" + teamId), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getMatchesByMultipleIDs(String[] fixtureIds, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/multi/" + String.join(",", fixtureIds)), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getLatestUpdatedLivescores(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("livescores/latest"), includes).build();
        return gson().fromJson(execute(url), MatchsResponse.class).getData();
    }

    public List<MatchDetail> getFixturesByHeadToHead(long team1Id, long team2Id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/head-to-head/" + team1Id + "/" + team2Id), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> searchFixtures(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/search/" + name), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getLatestUpdatedFixtures(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/latest"), includes).build();
        return gson().fromJson(execute(url), MatchsResponse.class).getData();
    }

    public List<MatchDetail> getUpcomingFixturesByMarket(long marketId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/upcoming/markets/" + marketId), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getUpcomingFixturesByTvStation(long tvStationId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/upcoming/tv-stations/" + tvStationId), includes).build();
        return fetchMatchList(url);
    }

    public List<MatchDetail> getPastFixturesByTvStation(long tvStationId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/past/tv-stations/" + tvStationId), includes).build();
        return fetchMatchList(url);
    }

    public MatchDetail getMatchDetail(String matchId) throws IOException, SportMonksException {
        return getMatchDetail(matchId, "venue", "state", "lineups", "events", "statistics", "periods", "participants", "scores");
    }

    public MatchDetail getMatchDetail(String matchId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("fixtures/" + matchId), includes).build();
        MatchData data = gson().fromJson(execute(url), MatchData.class);
        return data.getMatchDetail();
    }

    // -------------------------------------------------------------------------
    // Seasons
    // -------------------------------------------------------------------------

    public List<SeasonData> getSeasons() throws IOException, SportMonksException {
        HttpUrl url = footballUrl("seasons").build();
        Gson g = gson();
        String body = execute(url);
        SeasonsResponse resp = g.fromJson(body, SeasonsResponse.class);
        List<SeasonData> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, SeasonsResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public SeasonData getSeasonById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons/" + id), includes).build();
        return gson().fromJson(execute(url), SeasonDataResponse.class).getSeasonData();
    }

    public List<SeasonData> getSeasonsByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons/teams/" + teamId), includes).build();
        Gson g = gson();
        String body = execute(url);
        SeasonsResponse resp = g.fromJson(body, SeasonsResponse.class);
        List<SeasonData> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, SeasonsResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public List<SeasonData> searchSeasons(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons/search/" + name), includes).build();
        Gson g = gson();
        String body = execute(url);
        SeasonsResponse resp = g.fromJson(body, SeasonsResponse.class);
        List<SeasonData> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, SeasonsResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public List<Bracket> getBracketsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons/" + seasonId + "/brackets"), includes).build();
        return gson().fromJson(execute(url), BracketsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Odds
    // -------------------------------------------------------------------------

    public List<Odd> getMatchOdds(String matchId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(oddsUrl("pre-match/fixtures/" + matchId), includes).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getMatchOddsByBookmaker(String matchId, String bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + matchId + "/bookmakers/" + bookmakerId).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getMatchOddsByMarket(String matchId, String marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + matchId + "/markets/" + marketId).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Top Scorers
    // -------------------------------------------------------------------------

    public List<TopScoresPlayer> getTopScores(String seasonId) throws IOException, SportMonksException {
        return getTopScores(seasonId, "season", "stage", "player", "type", "participant");
    }

    public List<TopScoresPlayer> getTopScores(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("topscorers/seasons/" + seasonId), includes).build();
        return fetchTopScoresList(url);
    }

    public List<TopScoresPlayer> getTopScoresFiltered(String seasonId, int typeId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("topscorers/seasons/" + seasonId), includes)
                .addQueryParameter("filters", "seasontopscorerTypes:" + typeId)
                .build();
        return fetchTopScoresList(url);
    }

    public List<TopScoresPlayer> getTopScoresByStage(String stageId) throws IOException, SportMonksException {
        return getTopScoresByStage(stageId, "season", "stage", "player", "type");
    }

    public List<TopScoresPlayer> getTopScoresByStage(String stageId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("topscorers/stages/" + stageId), includes).build();
        return fetchTopScoresList(url);
    }

    // -------------------------------------------------------------------------
    // Standings
    // -------------------------------------------------------------------------

    public List<Standings> getStandings(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("standings/seasons/" + seasonId), includes).build();
        StandingsResponse resp = gson().fromJson(execute(url), StandingsResponse.class);
        return resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public TreeMap<String, List<Standings>> getStandingsCup(String seasonId, String... includes) throws IOException, SportMonksException {
        TreeMap<String, List<Standings>> map = new TreeMap<>();
        for (Standings s : getStandings(seasonId, includes)) {
            map.computeIfAbsent(s.getGroup().getName(), k -> new ArrayList<>()).add(s);
        }
        return map;
    }

    // -------------------------------------------------------------------------
    // Venues
    // -------------------------------------------------------------------------

    public List<Venue> getVenues(String seasonId) throws IOException, SportMonksException {
        HttpUrl url = footballUrl("venues/seasons/" + seasonId).build();
        VenueResponse resp = gson().fromJson(execute(url), VenueResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Teams
    // -------------------------------------------------------------------------

    public List<Team> getTeams(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("teams/seasons/" + seasonId), includes).build();
        TeamsResponse resp = gson().fromJson(execute(url), TeamsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Team getTeam(String teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("teams/" + teamId), includes).build();
        return gson().fromJson(execute(url), Team.class);
    }

    // -------------------------------------------------------------------------
    // Leagues
    // -------------------------------------------------------------------------

    public List<League> getAllLeagues(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues"), includes).build();
        Gson g = gson();
        String body = execute(url);
        LeaguesResponse resp = g.fromJson(body, LeaguesResponse.class);
        List<League> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, LeaguesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public League getLeagueById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/" + id), includes).build();
        LeagueResponse resp = gson().fromJson(execute(url), LeagueResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<League> getLiveLeagues(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/live"), includes).build();
        Gson g = gson();
        String body = execute(url);
        LeaguesResponse resp = g.fromJson(body, LeaguesResponse.class);
        List<League> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, LeaguesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public List<League> getLeaguesByDate(String date, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/date/" + date), includes).build();
        Gson g = gson();
        String body = execute(url);
        LeaguesResponse resp = g.fromJson(body, LeaguesResponse.class);
        List<League> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, LeaguesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public List<League> getLeaguesByCountry(long countryId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/countries/" + countryId), includes).build();
        Gson g = gson();
        String body = execute(url);
        LeaguesResponse resp = g.fromJson(body, LeaguesResponse.class);
        List<League> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, LeaguesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public List<League> searchLeagues(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/search/" + name), includes).build();
        Gson g = gson();
        String body = execute(url);
        LeaguesResponse resp = g.fromJson(body, LeaguesResponse.class);
        List<League> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, LeaguesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public List<League> getLeaguesByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/teams/" + teamId), includes).build();
        Gson g = gson();
        String body = execute(url);
        LeaguesResponse resp = g.fromJson(body, LeaguesResponse.class);
        List<League> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, LeaguesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public List<League> getCurrentLeaguesByTeam(long teamId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/teams/" + teamId + "/current"), includes).build();
        Gson g = gson();
        String body = execute(url);
        LeaguesResponse resp = g.fromJson(body, LeaguesResponse.class);
        List<League> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, LeaguesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    // -------------------------------------------------------------------------
    // Stages
    // -------------------------------------------------------------------------

    public List<Stage> getAllStages(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages"), includes).build();
        StagesResponse resp = gson().fromJson(execute(url), StagesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Stage> getStagesBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages/seasons/" + seasonId), includes).build();
        StagesResponse resp = gson().fromJson(execute(url), StagesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Stage getStageById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages/" + id), includes).build();
        StageResponse resp = gson().fromJson(execute(url), StageResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Stage> searchStages(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages/search/" + name), includes).build();
        StagesResponse resp = gson().fromJson(execute(url), StagesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Rounds
    // -------------------------------------------------------------------------

    public List<Round> getAllRounds(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds"), includes).build();
        RoundsResponse resp = gson().fromJson(execute(url), RoundsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<Round> getRoundsBySeasonId(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds/seasons/" + seasonId), includes).build();
        RoundsResponse resp = gson().fromJson(execute(url), RoundsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Round getRoundById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds/" + id), includes).build();
        RoundResponse resp = gson().fromJson(execute(url), RoundResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Round> searchRounds(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds/search/" + name), includes).build();
        RoundsResponse resp = gson().fromJson(execute(url), RoundsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // States
    // -------------------------------------------------------------------------

    public List<State> getAllStates() throws IOException, SportMonksException {
        HttpUrl url = footballUrl("states").build();
        return gson().fromJson(execute(url), StatesResponse.class).getData();
    }

    public State getStateById(long id) throws IOException, SportMonksException {
        HttpUrl url = footballUrl("states/" + id).build();
        return gson().fromJson(execute(url), StateResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Continents
    // -------------------------------------------------------------------------

    public List<Continent> getAllContinents(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("continents"), includes).build();
        Gson g = gson();
        String body = execute(url);
        ContinentsResponse resp = g.fromJson(body, ContinentsResponse.class);
        List<Continent> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, ContinentsResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public Continent getContinentById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("continents/" + id), includes).build();
        return gson().fromJson(execute(url), ContinentResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Countries
    // -------------------------------------------------------------------------

    public List<Country> getAllCountries(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("countries"), includes).build();
        Gson g = gson();
        String body = execute(url);
        CountriesResponse resp = g.fromJson(body, CountriesResponse.class);
        List<Country> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, CountriesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public Country getCountryById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("countries/" + id), includes).build();
        return gson().fromJson(execute(url), CountryResponse.class).getData();
    }

    public List<Country> searchCountries(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("countries/search/" + name), includes).build();
        Gson g = gson();
        String body = execute(url);
        CountriesResponse resp = g.fromJson(body, CountriesResponse.class);
        List<Country> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, CountriesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    // -------------------------------------------------------------------------
    // Core — Regions
    // -------------------------------------------------------------------------

    public List<Region> getAllRegions(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("regions"), includes).build();
        Gson g = gson();
        String body = execute(url);
        RegionsResponse resp = g.fromJson(body, RegionsResponse.class);
        List<Region> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, RegionsResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public Region getRegionById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("regions/" + id), includes).build();
        return gson().fromJson(execute(url), RegionResponse.class).getData();
    }

    public List<Region> searchRegions(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("regions/search/" + name), includes).build();
        Gson g = gson();
        String body = execute(url);
        RegionsResponse resp = g.fromJson(body, RegionsResponse.class);
        List<Region> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, RegionsResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    // -------------------------------------------------------------------------
    // Core — Cities
    // -------------------------------------------------------------------------

    public List<City> getAllCities(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("cities"), includes).build();
        Gson g = gson();
        String body = execute(url);
        CitiesResponse resp = g.fromJson(body, CitiesResponse.class);
        List<City> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, CitiesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public City getCityById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("cities/" + id), includes).build();
        return gson().fromJson(execute(url), CityResponse.class).getData();
    }

    public List<City> searchCities(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("cities/search/" + name), includes).build();
        Gson g = gson();
        String body = execute(url);
        CitiesResponse resp = g.fromJson(body, CitiesResponse.class);
        List<City> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, CitiesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    // -------------------------------------------------------------------------
    // Core — Types
    // -------------------------------------------------------------------------

    public List<Type> getAllTypes(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("types"), includes).build();
        Gson g = gson();
        String body = execute(url);
        TypesResponse resp = g.fromJson(body, TypesResponse.class);
        List<Type> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, TypesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public Type getTypeById(long id) throws IOException, SportMonksException {
        HttpUrl url = coreUrl("types/" + id).build();
        return gson().fromJson(execute(url), TypeResponse.class).getData();
    }

    public List<Type> getTypesByEntity(String entity) throws IOException, SportMonksException {
        HttpUrl url = coreUrl("types/entities/" + entity).build();
        return gson().fromJson(execute(url), TypesResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Timezones
    // -------------------------------------------------------------------------

    public List<String> getAllTimezones() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("timezones").build();
        return gson().fromJson(execute(url), TimezonesResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Filters
    // -------------------------------------------------------------------------

    public java.util.Map<String, List<String>> getAllEntityFilters() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("filters/entities").build();
        return gson().fromJson(execute(url), FiltersResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // MySportmonks
    // -------------------------------------------------------------------------

    public MyApi getMyApi() throws IOException, SportMonksException {
        HttpUrl url = myUrl("api").build();
        return gson().fromJson(execute(url), MyApiResponse.class).getData();
    }

    public List<MyLeague> getMyLeagues(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(myUrl("leagues"), includes).build();
        Gson g = gson();
        String body = execute(url);
        MyLeaguesResponse resp = g.fromJson(body, MyLeaguesResponse.class);
        List<MyLeague> list = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = url.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            body = execute(paged);
            resp = g.fromJson(body, MyLeaguesResponse.class);
            list.addAll(resp.getData());
        }
        return list;
    }

    public List<String> getMyEnrichments() throws IOException, SportMonksException {
        HttpUrl url = myUrl("enrichments").build();
        return gson().fromJson(execute(url), MyEnrichmentsResponse.class).getData();
    }

    public List<MyResource> getMyResources() throws IOException, SportMonksException {
        HttpUrl url = myUrl("resources").build();
        return gson().fromJson(execute(url), MyResourcesResponse.class).getData();
    }

    public List<MyUsage> getMyUsage() throws IOException, SportMonksException {
        HttpUrl url = myUrl("usage").build();
        return gson().fromJson(execute(url), MyUsagesResponse.class).getData();
    }
}
