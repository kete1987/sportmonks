package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.expectedgoal.ExpectedGoal;
import es.com.kete1987.sportmonks.library.football.model.expectedgoal.ExpectedGoalsResponse;
import es.com.kete1987.sportmonks.library.football.model.news.NewsArticle;
import es.com.kete1987.sportmonks.library.football.model.news.NewsArticlesResponse;
import es.com.kete1987.sportmonks.library.football.model.prediction.Predictability;
import es.com.kete1987.sportmonks.library.football.model.prediction.PredictabilityResponse;
import es.com.kete1987.sportmonks.library.football.model.prediction.Probability;
import es.com.kete1987.sportmonks.library.football.model.prediction.ProbabilitiesResponse;
import es.com.kete1987.sportmonks.library.football.model.prediction.ValueBet;
import es.com.kete1987.sportmonks.library.football.model.prediction.ValueBetsResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.List;

/**
 * Predictions sub-API: win/draw/loss probabilities, value bets, expected goals (XG),
 * and pre/post-match news.
 *
 * <p>Can be used standalone ({@code new PredictionsApi("apiKey")}) or via the
 * {@link SportMonksAPI} facade ({@code api.getPredictions()}).
 */
public class PredictionsApi extends SportMonksApiBase {

    private final String footballBase;

    public PredictionsApi(String apiToken) {
        this(apiToken, null);
    }

    public PredictionsApi(String apiToken, String locale) {
        this(apiToken, locale, null);
    }

    public PredictionsApi(String apiToken, String locale, String timezone) {
        this(buildHttpClient(apiToken), Constants.BASE_URL_FOOTBALL, locale, timezone, new RateLimitTracker());
    }

    PredictionsApi(OkHttpClient client, String footballBase, String locale, String timezone, RateLimitTracker tracker) {
        super(client, locale, timezone, tracker);
        this.footballBase = footballBase;
    }

    private HttpUrl.Builder footballUrl(String path) {
        return timezoneUrl(localeUrl(HttpUrl.parse(footballBase + path).newBuilder()));
    }

    // -------------------------------------------------------------------------
    // Predictions
    // -------------------------------------------------------------------------

    public List<Probability> getAllProbabilities(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("predictions/probabilities"), includes).build();
        return gson().fromJson(execute(url), ProbabilitiesResponse.class).getData();
    }

    public List<Predictability> getPredictabilityByLeague(long leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("predictions/probabilities/leagues/" + leagueId), includes).build();
        return gson().fromJson(execute(url), PredictabilityResponse.class).getData();
    }

    public List<Probability> getProbabilitiesByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("predictions/probabilities/fixtures/" + fixtureId), includes).build();
        return gson().fromJson(execute(url), ProbabilitiesResponse.class).getData();
    }

    public List<ValueBet> getAllValueBets(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("predictions/value-bets"), includes).build();
        return gson().fromJson(execute(url), ValueBetsResponse.class).getData();
    }

    public List<ValueBet> getValueBetsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("predictions/value-bets/fixtures/" + fixtureId), includes).build();
        return gson().fromJson(execute(url), ValueBetsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Expected Goals (XG)
    // -------------------------------------------------------------------------

    public List<ExpectedGoal> getExpectedGoalsByTeam(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("expected/teams"), includes).build();
        return gson().fromJson(execute(url), ExpectedGoalsResponse.class).getData();
    }

    public List<ExpectedGoal> getExpectedGoalsByPlayer(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("expected/players"), includes).build();
        return gson().fromJson(execute(url), ExpectedGoalsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // News
    // -------------------------------------------------------------------------

    public List<NewsArticle> getAllPreMatchNews(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("news/pre-match"), includes).build();
        return gson().fromJson(execute(url), NewsArticlesResponse.class).getData();
    }

    public List<NewsArticle> getPreMatchNewsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("news/pre-match/seasons/" + seasonId), includes).build();
        return gson().fromJson(execute(url), NewsArticlesResponse.class).getData();
    }

    public List<NewsArticle> getPreMatchNewsForUpcoming(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("news/pre-match/upcoming"), includes).build();
        return gson().fromJson(execute(url), NewsArticlesResponse.class).getData();
    }

    public List<NewsArticle> getAllPostMatchNews(String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("news/post-match"), includes).build();
        return gson().fromJson(execute(url), NewsArticlesResponse.class).getData();
    }

    public List<NewsArticle> getPostMatchNewsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("news/post-match/seasons/" + seasonId), includes).build();
        return gson().fromJson(execute(url), NewsArticlesResponse.class).getData();
    }
}
