package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link RequestOptions} applied through {@code withOptions(...)} (#84): {@code select},
 * {@code order}, {@code sortBy} and {@code filters} reach every request, on every page of a walk
 * (offset and cursor), combine with the filters a method already sends, and leave the instance
 * they were applied to untouched.
 */
class RequestOptionsApiTest extends BaseApiTest {

    private static final String SELECT = "select";
    private static final String ORDER = "order";
    private static final String SORT_BY = "sortBy";
    private static final String FILTERS = "filters";
    private static final String PER_PAGE = "per_page";
    private static final String CURSOR = "cursor";
    private static final String STARTING_AT = "starting_at";
    private static final String SELECTED = "name,image_path";
    private static final String NAME = "name";
    private static final String COUNTRY_FILTER = "leagueCountries:462";
    private static final String DESC = "desc";
    private static final String LAST_OFFSET_PAGE = "paged_list_page2.json";
    private static final String FIRST_CURSOR_PAGE = "cursor_page1.json";
    private static final String LAST_CURSOR_PAGE = "cursor_page3.json";
    private static final String EMPTY_LIST = "{\"data\":[]}";
    private static final String TEAM_SELECT = "team_select.json";

    private static final RequestOptions FULL = RequestOptions.builder()
            .select(NAME, "image_path")
            .order(RequestOptions.Order.DESC)
            .sortBy(NAME)
            .filters(COUNTRY_FILTER)
            .build();

    private HttpUrl takeUrl() throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        return request.getRequestUrl();
    }

    private void enqueueJson(String body) {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-RateLimit-Remaining", "2998"));
    }

    // --- every option reaches the request ---

    @Test
    void select_reachesSingleResourceAndLeavesOtherFieldsNull() throws IOException, SportMonksException, InterruptedException {
        enqueue(TEAM_SELECT);

        Team team = api.withOptions(RequestOptions.builder().select(NAME, "image_path").build())
                .getTeamById(53);

        assertEquals(SELECTED, takeUrl().queryParameter(SELECT));
        assertEquals("Celtic", team.getName());
        assertNotNull(team.getImagePath());
        assertNull(team.getShortCode());
        assertNull(team.getFounded());
    }

    @Test
    void allOptions_reachTheFirstRequest() throws IOException, SportMonksException, InterruptedException {
        enqueue(LAST_OFFSET_PAGE);

        api.withOptions(FULL).getAllLeagues();

        HttpUrl url = takeUrl();
        assertEquals(SELECTED, url.queryParameter(SELECT));
        assertEquals(DESC, url.queryParameter(ORDER));
        assertEquals(NAME, url.queryParameter(SORT_BY));
        assertEquals(COUNTRY_FILTER, url.queryParameter(FILTERS));
    }

    @Test
    void sortByWithoutOrder_sendsOnlySortBy() throws IOException, SportMonksException, InterruptedException {
        enqueue(LAST_OFFSET_PAGE);

        api.withOptions(RequestOptions.builder().sortBy(STARTING_AT).build()).getAllFixtures(5);

        HttpUrl url = takeUrl();
        assertEquals(STARTING_AT, url.queryParameter(SORT_BY));
        assertNull(url.queryParameter(ORDER));
        assertNull(url.queryParameter(SELECT));
        assertNull(url.queryParameter(FILTERS));
    }

    // --- pagination ---

    @Test
    void offsetWalk_carriesOptionsOnEveryPage() throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_list_page1.json");
        enqueue(LAST_OFFSET_PAGE);

        assertEquals(4, api.withOptions(FULL).getAllLeagues().size());

        takeUrl();
        HttpUrl second = takeUrl();
        assertEquals("2", second.queryParameter("page"));
        assertEquals(SELECTED, second.queryParameter(SELECT));
        assertEquals(DESC, second.queryParameter(ORDER));
        assertEquals(NAME, second.queryParameter(SORT_BY));
        assertEquals(COUNTRY_FILTER, second.queryParameter(FILTERS));
        assertNotNull(second.queryParameter(PER_PAGE));
    }

    /** The API answers 400 "The cursor is not valid for this request" if order/sortBy/select are dropped. */
    @Test
    void cursorWalk_carriesOptionsButNeverPerPage() throws IOException, SportMonksException, InterruptedException {
        enqueue(FIRST_CURSOR_PAGE);
        enqueue(LAST_CURSOR_PAGE);

        api.withOptions(FULL).getAllTransfers();

        assertNotNull(takeUrl().queryParameter(PER_PAGE));
        HttpUrl second = takeUrl();
        assertNotNull(second.queryParameter(CURSOR));
        assertNull(second.queryParameter(PER_PAGE));
        assertEquals(SELECTED, second.queryParameter(SELECT));
        assertEquals(DESC, second.queryParameter(ORDER));
        assertEquals(NAME, second.queryParameter(SORT_BY));
        assertEquals(COUNTRY_FILTER, second.queryParameter(FILTERS));
    }

    @Test
    void cursorWalk_doesNotRepeatOptionFiltersPageAfterPage() throws IOException, SportMonksException, InterruptedException {
        enqueue(FIRST_CURSOR_PAGE);
        enqueue("cursor_page2.json");
        enqueue(LAST_CURSOR_PAGE);

        api.withOptions(FULL).getAllTransfers();

        takeUrl();
        takeUrl();
        HttpUrl third = takeUrl();
        assertEquals(1, third.queryParameterValues(FILTERS).size());
        assertEquals(COUNTRY_FILTER, third.queryParameter(FILTERS));
        assertEquals(1, third.queryParameterValues(ORDER).size());
    }

    // --- filters combine with the ones of the method ---

    @Test
    void methodFilters_comeFirstAndOptionFiltersAreAppendedWithSemicolon() throws IOException, SportMonksException, InterruptedException {
        enqueueJson("{\"data\":{\"id\":1}}");

        api.withOptions(RequestOptions.builder().filters("eventTypes:14").build())
                .getMatchDetail("1", new String[]{"lineups.details"}, "lineupdetailTypes:40");

        HttpUrl url = takeUrl();
        assertEquals(1, url.queryParameterValues(FILTERS).size());
        assertEquals("lineupdetailTypes:40;eventTypes:14", url.queryParameter(FILTERS));
    }

    @Test
    void topScoresFiltered_combinesFixedTypeFilterWithOptions() throws IOException, SportMonksException, InterruptedException {
        enqueue(LAST_OFFSET_PAGE);

        api.withOptions(RequestOptions.builder().filters("seasontopscorerPlayers:460810").build())
                .getTopScoresFiltered("19735", 208, 5);

        assertEquals("seasontopscorerTypes:208;seasontopscorerPlayers:460810", takeUrl().queryParameter(FILTERS));
    }

    @Test
    void oddsFilters_combineWithOptionFilters() throws IOException, SportMonksException, InterruptedException {
        enqueueJson(EMPTY_LIST);

        api.withOptions(RequestOptions.builder().filters("WinningOdds").order(RequestOptions.Order.DESC).build())
                .getMatchOdds("18545372", new String[0], "markets:1");

        HttpUrl url = takeUrl();
        assertEquals("markets:1;WinningOdds", url.queryParameter(FILTERS));
        assertEquals(DESC, url.queryParameter(ORDER));
    }

    // --- no options, immutability, shared state ---

    @Test
    void viewWithoutOptions_sendsExactlyTheSameRequestAsBefore() throws IOException, SportMonksException, InterruptedException {
        enqueue(TEAM_SELECT);
        enqueue(TEAM_SELECT);

        api.getTeamById(53, "venue");
        api.withOptions(RequestOptions.none()).getTeamById(53, "venue");

        assertEquals(takeUrl(), takeUrl());
    }

    @Test
    void viewWithoutOptions_pagesExactlyAsBefore() throws IOException, SportMonksException, InterruptedException {
        enqueue(FIRST_CURSOR_PAGE);
        enqueue(LAST_CURSOR_PAGE);
        enqueue(FIRST_CURSOR_PAGE);
        enqueue(LAST_CURSOR_PAGE);

        api.getAllTransfers();
        api.withOptions(RequestOptions.builder().build()).getAllTransfers();

        HttpUrl first = takeUrl();
        HttpUrl second = takeUrl();
        assertEquals(first, takeUrl());
        assertEquals(second, takeUrl());
    }

    @Test
    void withOptions_leavesTheOriginalInstanceUntouched() throws IOException, SportMonksException, InterruptedException {
        enqueue(TEAM_SELECT);

        SportMonksAPI view = api.withOptions(FULL);
        api.getTeamById(53);

        assertNotSame(api, view);
        HttpUrl url = takeUrl();
        assertNull(url.queryParameter(SELECT));
        assertNull(url.queryParameter(ORDER));
        assertNull(url.queryParameter(SORT_BY));
        assertNull(url.queryParameter(FILTERS));
        assertEquals(RequestOptions.none(), api.getFootball().getRequestOptions());
        assertEquals(FULL, view.getFootball().getRequestOptions());
    }

    @Test
    void withOptions_replacesThePreviousOptionsOfAView() throws IOException, SportMonksException, InterruptedException {
        enqueue(TEAM_SELECT);

        api.withOptions(FULL).withOptions(RequestOptions.builder().order(RequestOptions.Order.ASC).build()).getTeamById(53);

        HttpUrl url = takeUrl();
        assertEquals("asc", url.queryParameter(ORDER));
        assertNull(url.queryParameter(SELECT));
    }

    @Test
    void view_sharesRateLimitStateWithTheOriginal() throws IOException, SportMonksException {
        enqueueJson("{\"data\":{\"id\":53}}");

        api.withOptions(FULL).getTeamById(53);

        assertEquals("2998", api.getRemainingRequests());
    }

    // --- sub-APIs ---

    @Test
    void coreView_appliesOptions() throws IOException, SportMonksException, InterruptedException {
        enqueueJson(EMPTY_LIST);

        api.getCore().withOptions(FULL).getAllCountries();

        assertEquals(DESC, takeUrl().queryParameter(ORDER));
    }

    @Test
    void oddsView_appliesOptionsToTheCatalogue() throws IOException, SportMonksException, InterruptedException {
        enqueueJson(EMPTY_LIST);

        api.getOdds().withOptions(FULL).getAllMarkets();

        HttpUrl url = takeUrl();
        assertTrue(url.encodedPath().startsWith("/v3/odds/markets"), url.encodedPath());
        assertEquals(SELECTED, url.queryParameter(SELECT));
    }

    @Test
    void predictionsView_appliesOptions() throws IOException, SportMonksException, InterruptedException {
        enqueueJson(EMPTY_LIST);

        api.getPredictions().withOptions(FULL).getValueBetsByFixture(1L);

        assertEquals(NAME, takeUrl().queryParameter(SORT_BY));
    }

    @Test
    void facadeView_exposesSubApiViewsWithTheSameOptions() {
        SportMonksAPI view = api.withOptions(FULL);

        assertEquals(FULL, view.getFootball().getRequestOptions());
        assertEquals(FULL, view.getCore().getRequestOptions());
        assertEquals(FULL, view.getOdds().getRequestOptions());
        assertEquals(FULL, view.getPredictions().getRequestOptions());
    }

    @Test
    void subApiView_leavesTheSubApiUntouched() {
        FootballApi football = api.getFootball();

        FootballApi view = football.withOptions(FULL);

        assertNotSame(football, view);
        assertEquals(RequestOptions.none(), football.getRequestOptions());
    }

    @Test
    void withOptions_rejectsNull() {
        FootballApi football = api.getFootball();
        assertThrows(NullPointerException.class, () -> football.withOptions(null));
    }
}
