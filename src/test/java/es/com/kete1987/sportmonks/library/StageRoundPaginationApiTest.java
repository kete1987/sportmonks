package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StageRoundPaginationApiTest extends BaseApiTest {
    private static final String[] INCLUDES = {"fixtures.lineups.details"};
    private static final String FILTERS = "lineupdetailTypes:40";

    @FunctionalInterface
    interface FilteredCall {
        List<?> call(SportMonksAPI api, String[] includes, String filters) throws IOException, SportMonksException;
    }

    @FunctionalInterface
    interface LegacyCall {
        List<?> call(SportMonksAPI api, String[] includes) throws IOException, SportMonksException;
    }

    static Stream<Arguments> paginatedCalls() {
        return Stream.of(
                collection("/stages", SportMonksAPI::getAllStages, SportMonksAPI::getAllStages),
                collection("/stages/seasons/23614",
                        (a, i, f) -> a.getStagesBySeasonId(23614, i, f), (a, i) -> a.getStagesBySeasonId(23614, i)),
                collection("/stages/search/Regular",
                        (a, i, f) -> a.searchStages("Regular", i, f), (a, i) -> a.searchStages("Regular", i)),
                collection("/rounds", SportMonksAPI::getAllRounds, SportMonksAPI::getAllRounds),
                collection("/rounds/seasons/23614",
                        (a, i, f) -> a.getRoundsBySeasonId(23614, i, f), (a, i) -> a.getRoundsBySeasonId(23614, i)),
                collection("/rounds/search/Matchday",
                        (a, i, f) -> a.searchRounds("Matchday", i, f), (a, i) -> a.searchRounds("Matchday", i))
        ).flatMap(Function.identity());
    }

    private static Stream<Arguments> collection(String path, FilteredCall filtered, LegacyCall legacy) {
        return Stream.of(false, true).flatMap(cursor -> Stream.of(false, true)
                .map(useFilters -> Arguments.of(path, cursor, useFilters, filtered, legacy)));
    }

    @ParameterizedTest(name = "{0}, cursor={1}, filtered={2}")
    @MethodSource("paginatedCalls")
    void collectionFetchesAllPagesAndKeepsQuery(String path, boolean cursor, boolean useFilters,
                                              FilteredCall filtered, LegacyCall legacy)
            throws IOException, SportMonksException, InterruptedException {
        enqueue(cursor ? "cursor_page1.json" : "paged_list_page1.json");
        enqueue(cursor ? "cursor_page3.json" : "paged_list_page2.json");

        List<?> result = useFilters ? filtered.call(api, INCLUDES, FILTERS) : legacy.call(api, INCLUDES);
        List<Long> expected = cursor ? Arrays.asList(1L, 2L, 5L) : Arrays.asList(1L, 2L, 3L, 4L);
        assertEquals(expected, result.stream().map(StageRoundPaginationApiTest::id).collect(Collectors.toList()));
        assertEquals(2, server.getRequestCount());

        HttpUrl first = server.takeRequest().getRequestUrl();
        HttpUrl second = server.takeRequest().getRequestUrl();
        for (HttpUrl request : new HttpUrl[]{first, second}) {
            assertEquals(path, request.encodedPath());
            assertEquals(INCLUDES[0], request.queryParameter("include"));
            assertEquals(useFilters ? FILTERS : null, request.queryParameter("filters"));
        }
        assertNull(first.queryParameter("cursor"));
        assertNull(first.queryParameter("page"));
        if (cursor) {
            assertEquals("Y1VSU09SLVBBR0UtMg", second.queryParameter("cursor"));
            assertNull(second.queryParameter("page"));
        } else {
            assertEquals("2", second.queryParameter("page"));
            assertNull(second.queryParameter("cursor"));
        }
    }

    private static Long id(Object item) {
        return item instanceof Stage ? ((Stage) item).getId() : ((Round) item).getId();
    }

    static Stream<Arguments> emptyResponses() {
        return Stream.of("null", "{\"data\":null}", "{\"data\":[]}")
                .flatMap(body -> Stream.of(false, true).map(stages -> Arguments.of(body, stages)));
    }

    @ParameterizedTest
    @MethodSource("emptyResponses")
    void absentOrEmptyCollectionsStillReturnEmptyList(String body, boolean stages) throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody(body));
        List<?> result = stages ? api.getAllStages(INCLUDES, FILTERS) : api.getAllRounds(INCLUDES, FILTERS);
        assertTrue(result.isEmpty());
        assertEquals(1, server.getRequestCount());
    }
}
