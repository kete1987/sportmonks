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
import es.com.kete1987.sportmonks.library.core.model.my.MyLeague;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeaguesResponse;
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
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.rounds.RoundResponse;
import es.com.kete1987.sportmonks.library.football.model.rounds.RoundsResponse;
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
                Constants.baseURLCore);
    }

    SportMonksAPI(OkHttpClient client, String footballBase, String oddsBase, String coreBase) {
        this.httpClient = client;
        this.footballBase = footballBase;
        this.oddsBase = oddsBase;
        this.coreBase = coreBase;
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

    public SeasonData getSeasonData(String seasonId) throws IOException, SportMonksException {
        return getSeasonData(seasonId, "stages", "fixtures");
    }

    public SeasonData getSeasonData(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("seasons/" + seasonId), includes).build();
        return gson().fromJson(execute(url), SeasonDataResponse.class).getSeasonData();
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

    public List<League> getLeagues(String... includes) throws IOException, SportMonksException {
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

    public League getLeague(String leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("leagues/" + leagueId), includes).build();
        LeagueResponse resp = gson().fromJson(execute(url), LeagueResponse.class);
        return resp != null ? resp.getData() : null;
    }

    // -------------------------------------------------------------------------
    // Stages
    // -------------------------------------------------------------------------

    public List<Stage> getStages(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages/seasons/" + seasonId), includes).build();
        StagesResponse resp = gson().fromJson(execute(url), StagesResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Stage getStage(String stageId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("stages/" + stageId), includes).build();
        StageResponse resp = gson().fromJson(execute(url), StageResponse.class);
        return resp != null ? resp.getData() : null;
    }

    // -------------------------------------------------------------------------
    // Rounds
    // -------------------------------------------------------------------------

    public List<Round> getRounds(String seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds/seasons/" + seasonId), includes).build();
        RoundsResponse resp = gson().fromJson(execute(url), RoundsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public Round getRound(String roundId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("rounds/" + roundId), includes).build();
        RoundResponse resp = gson().fromJson(execute(url), RoundResponse.class);
        return resp != null ? resp.getData() : null;
    }

    // -------------------------------------------------------------------------
    // Core — Continents
    // -------------------------------------------------------------------------

    public List<Continent> getContinents(String... includes) throws IOException, SportMonksException {
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

    public Continent getContinent(String continentId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("continents/" + continentId), includes).build();
        return gson().fromJson(execute(url), ContinentResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Countries
    // -------------------------------------------------------------------------

    public List<Country> getCountries(String... includes) throws IOException, SportMonksException {
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

    public Country getCountry(String countryId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("countries/" + countryId), includes).build();
        return gson().fromJson(execute(url), CountryResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Regions
    // -------------------------------------------------------------------------

    public List<Region> getRegions(String... includes) throws IOException, SportMonksException {
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

    public Region getRegion(String regionId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("regions/" + regionId), includes).build();
        return gson().fromJson(execute(url), RegionResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Cities
    // -------------------------------------------------------------------------

    public List<City> getCities(String... includes) throws IOException, SportMonksException {
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

    public City getCity(String cityId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("cities/" + cityId), includes).build();
        return gson().fromJson(execute(url), CityResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Types
    // -------------------------------------------------------------------------

    public List<Type> getTypes(String... includes) throws IOException, SportMonksException {
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

    public Type getType(String typeId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("types/" + typeId), includes).build();
        return gson().fromJson(execute(url), TypeResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Timezones
    // -------------------------------------------------------------------------

    public List<String> getTimezones() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("timezones").build();
        return gson().fromJson(execute(url), TimezonesResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — Filters
    // -------------------------------------------------------------------------

    public java.util.Map<String, List<String>> getFilters() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("filters").build();
        return gson().fromJson(execute(url), FiltersResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Core — MySportmonks
    // -------------------------------------------------------------------------

    public MyApi getMyApi() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("my/api").build();
        return gson().fromJson(execute(url), MyApiResponse.class).getData();
    }

    public List<MyLeague> getMyLeagues(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("my/leagues"), includes).build();
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
}
