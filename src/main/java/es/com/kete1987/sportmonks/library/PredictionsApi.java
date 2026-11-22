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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Predictions sub-API: win/draw/loss probabilities, value bets, expected goals (XG),
 * and pre/post-match news.
 *
 * <p><b>Not validated against real responses.</b> The test subscription does not cover
 * {@code predictions/*}, {@code expected/*} or {@code news/*} (HTTP 403), so the routes were
 * checked only by the 404 (route missing) versus 403 (route exists) difference, and the models
 * ({@link Probability}, {@link ValueBet}, {@link Predictability}, {@link ExpectedGoal},
 * {@link NewsArticle}) could not be compared with real JSON. Known gaps against the documentation:
 * the documented probability entity nests its values under {@code predictions} (home/draw/away or
 * yes/no), the documented Expected entity is {@code id, fixture_id, type_id, value}, and the live
 * probabilities ({@code predictions/live/probabilities}, a distinct entity with {@code period_id},
 * {@code minute} and cursor pagination) are not wrapped yet.
 *
 * <p>Every list method returns an empty list, never {@code null}, when the body is empty or has
 * no {@code data}.
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
        this(apiToken, locale, timezone, ClientOptions.defaults());
    }

    /**
     * Creates the client with custom HTTP settings (timeouts, an own {@code OkHttpClient}).
     * Pass {@code null} for the default locale or timezone.
     */
    public PredictionsApi(String apiToken, String locale, String timezone, ClientOptions options) {
        this(buildHttpClient(apiToken, options), Constants.BASE_URL_FOOTBALL, locale, timezone, new RateLimitTracker());
    }

    PredictionsApi(OkHttpClient client, String footballBase, String locale, String timezone, RateLimitTracker tracker) {
        this(client, footballBase, locale, timezone, tracker, RequestOptions.none());
    }

    private PredictionsApi(OkHttpClient client, String footballBase, String locale, String timezone,
                           RateLimitTracker tracker, RequestOptions options) {
        super(client, locale, timezone, tracker, options);
        this.footballBase = footballBase;
    }

    /**
     * A view of this client that adds {@code options} to every request; see
     * {@link FootballApi#withOptions(RequestOptions)}. Not checked against these routes, which the
     * test subscription cannot reach (403).
     */
    public PredictionsApi withOptions(RequestOptions options) {
        return new PredictionsApi(httpClient, footballBase, locale, timezone, rateLimitTracker, options);
    }

    private HttpUrl.Builder footballUrl(String path, Object... segments) {
        return apiUrl(footballBase, path, segments);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** {@code data} of a response, or an empty list when the body is empty or carries no {@code data}. */
    private static <R, T> List<T> dataOrEmpty(R resp, Function<R, List<T>> dataFn) {
        List<T> data = resp != null ? dataFn.apply(resp) : null;
        return data != null ? data : new ArrayList<>();
    }

    private List<Probability> probabilities(HttpUrl url) throws IOException, SportMonksException {
        return dataOrEmpty(get(url, ProbabilitiesResponse.class), ProbabilitiesResponse::getData);
    }

    private List<ValueBet> valueBets(HttpUrl url) throws IOException, SportMonksException {
        return dataOrEmpty(get(url, ValueBetsResponse.class), ValueBetsResponse::getData);
    }

    private List<ExpectedGoal> expected(HttpUrl url) throws IOException, SportMonksException {
        return dataOrEmpty(get(url, ExpectedGoalsResponse.class), ExpectedGoalsResponse::getData);
    }

    private List<NewsArticle> news(HttpUrl url) throws IOException, SportMonksException {
        return dataOrEmpty(get(url, NewsArticlesResponse.class), NewsArticlesResponse::getData);
    }

    // -------------------------------------------------------------------------
    // Predictions
    // -------------------------------------------------------------------------

    /**
     * {@code GET predictions/probabilities}. Not validated with real responses (403 with the test
     * subscription). The documentation mentions {@code page}/{@code per_page}, but the response
     * body of a paginated reply could not be confirmed, so only the first page is returned.
     */
    public List<Probability> getAllProbabilities(String... includes) throws IOException, SportMonksException {
        return probabilities(withIncludes(footballUrl("predictions/probabilities"), includes).build());
    }

    /**
     * {@code GET predictions/predictability/leagues/{id}} (it used to call
     * {@code predictions/probabilities/leagues}, which does not exist: 404). Route confirmed
     * (403 with the test subscription); the model is not validated with real responses.
     */
    public List<Predictability> getPredictabilityByLeague(long leagueId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(footballUrl("predictions/predictability/leagues", leagueId), includes).build();
        return dataOrEmpty(get(url, PredictabilityResponse.class), PredictabilityResponse::getData);
    }

    /** {@code GET predictions/probabilities/fixtures/{id}}. Not validated with real responses (403). */
    public List<Probability> getProbabilitiesByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        return probabilities(withIncludes(footballUrl("predictions/probabilities/fixtures", fixtureId), includes).build());
    }

    /** {@code GET predictions/value-bets}. Not validated with real responses (403); first page only. */
    public List<ValueBet> getAllValueBets(String... includes) throws IOException, SportMonksException {
        return valueBets(withIncludes(footballUrl("predictions/value-bets"), includes).build());
    }

    /** {@code GET predictions/value-bets/fixtures/{id}}. Not validated with real responses (403). */
    public List<ValueBet> getValueBetsByFixture(long fixtureId, String... includes) throws IOException, SportMonksException {
        return valueBets(withIncludes(footballUrl("predictions/value-bets/fixtures", fixtureId), includes).build());
    }

    // -------------------------------------------------------------------------
    // Expected Goals (XG)
    // -------------------------------------------------------------------------

    /**
     * {@code GET expected/fixtures}: expected values per fixture. Replaces
     * {@code getExpectedGoalsByTeam}, which called {@code expected/teams} (404, never worked).
     * Route confirmed (403 with the test subscription). The documented Expected entity is
     * {@code id, fixture_id, type_id, value}, which differs from {@link ExpectedGoal}
     * ({@code possession}, {@code goals_expected}, ...); the model was left unchanged because it
     * could not be validated with real responses.
     */
    public List<ExpectedGoal> getExpectedGoalsByFixture(String... includes) throws IOException, SportMonksException {
        return expected(withIncludes(footballUrl("expected/fixtures"), includes).build());
    }

    /**
     * {@code GET expected/lineups}: expected values per lineup (player). Replaces
     * {@code getExpectedGoalsByPlayer}, which called {@code expected/players} (404, never worked).
     * Route confirmed (403 with the test subscription). The documented entity carries a
     * {@code player_id} next to {@code type_id} and {@code value}, unlike {@link ExpectedGoal};
     * the model could not be validated with real responses.
     */
    public List<ExpectedGoal> getExpectedGoalsByLineup(String... includes) throws IOException, SportMonksException {
        return expected(withIncludes(footballUrl("expected/lineups"), includes).build());
    }

    // -------------------------------------------------------------------------
    // News
    // -------------------------------------------------------------------------

    /** {@code GET news/pre-match}. Not validated with real responses (403). */
    public List<NewsArticle> getAllPreMatchNews(String... includes) throws IOException, SportMonksException {
        return news(withIncludes(footballUrl("news/pre-match"), includes).build());
    }

    /** {@code GET news/pre-match/seasons/{id}}. Not validated with real responses (403). */
    public List<NewsArticle> getPreMatchNewsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        return news(withIncludes(footballUrl("news/pre-match/seasons", seasonId), includes).build());
    }

    /** {@code GET news/pre-match/upcoming}. Not validated with real responses (403). */
    public List<NewsArticle> getPreMatchNewsForUpcoming(String... includes) throws IOException, SportMonksException {
        return news(withIncludes(footballUrl("news/pre-match/upcoming"), includes).build());
    }

    /** {@code GET news/post-match}. Not validated with real responses (403). */
    public List<NewsArticle> getAllPostMatchNews(String... includes) throws IOException, SportMonksException {
        return news(withIncludes(footballUrl("news/post-match"), includes).build());
    }

    /** {@code GET news/post-match/seasons/{id}}. Not validated with real responses (403). */
    public List<NewsArticle> getPostMatchNewsBySeason(long seasonId, String... includes) throws IOException, SportMonksException {
        return news(withIncludes(footballUrl("news/post-match/seasons", seasonId), includes).build());
    }
}
