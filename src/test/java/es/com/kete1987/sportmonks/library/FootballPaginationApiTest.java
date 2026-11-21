package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import okhttp3.HttpUrl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The list methods of issue #82 that used to stop after the first page: each must follow the
 * cursor to the second page, keep its includes, and honour {@code limit} through {@code per_page}.
 */
class FootballPaginationApiTest extends BaseApiTest {

    @FunctionalInterface
    interface ListCall {
        List<?> call(SportMonksAPI api) throws IOException, SportMonksException;
    }

    static Stream<Arguments> unlimited() {
        return Stream.of(
                Arguments.of("/teams/countries/32", (ListCall) a -> a.getTeamsByCountry(32L, "country")),
                Arguments.of("/referees/countries/32", (ListCall) a -> a.getRefereesByCountry(32L, "country")),
                Arguments.of("/coaches/countries/32", (ListCall) a -> a.getCoachesByCountry(32L, "country")),
                Arguments.of("/venues", (ListCall) a -> a.getAllVenues("country")),
                Arguments.of("/venues/search/Estadio", (ListCall) a -> a.searchVenues("Estadio", "country")),
                Arguments.of("/rivals", (ListCall) a -> a.getAllRivals("country")),
                Arguments.of("/commentaries", (ListCall) a -> a.getAllCommentaries("country")),
                Arguments.of("/statistics/stages/77", (ListCall) a -> a.getStatsByStage(77L, "country")),
                Arguments.of("/statistics/rounds/88", (ListCall) a -> a.getStatsByRound(88L, "country")),
                Arguments.of("/transfers/latest", (ListCall) a -> a.getLatestTransfers("country")),
                Arguments.of("/transfers/between/2026-07-01/2026-07-31", (ListCall) a -> a.getTransfersByDateRange("2026-07-01", "2026-07-31", "country")),
                Arguments.of("/teams/search/united", (ListCall) a -> a.searchTeams("united", "country")),
                Arguments.of("/players/search/silva", (ListCall) a -> a.searchPlayers("silva", "country")),
                Arguments.of("/coaches/search/garcia", (ListCall) a -> a.searchCoaches("garcia", "country")),
                Arguments.of("/referees/search/garcia", (ListCall) a -> a.searchReferees("garcia", "country")),
                Arguments.of("/referees/seasons/13133", (ListCall) a -> a.getRefereesBySeason(13133L, "country")),
                Arguments.of("/standings", (ListCall) a -> a.getAllStandings("country")),
                Arguments.of("/match-facts/1001", (ListCall) a -> a.getMatchFactsByFixture(1001L, "country")),
                Arguments.of("/match-facts/fixtures/between/2026-10-01/2026-10-05", (ListCall) a -> a.getMatchFactsByDateRange("2026-10-01", "2026-10-05", "country")));
    }

    static Stream<Arguments> limited() {
        return Stream.of(
                Arguments.of("/teams/countries/32", (ListCall) a -> a.getTeamsByCountry(32L, 2)),
                Arguments.of("/referees/countries/32", (ListCall) a -> a.getRefereesByCountry(32L, 2)),
                Arguments.of("/coaches/countries/32", (ListCall) a -> a.getCoachesByCountry(32L, 2)),
                Arguments.of("/venues", (ListCall) a -> a.getAllVenues(2)),
                Arguments.of("/venues/search/Estadio", (ListCall) a -> a.searchVenues("Estadio", 2)),
                Arguments.of("/rivals", (ListCall) a -> a.getAllRivals(2)),
                Arguments.of("/commentaries", (ListCall) a -> a.getAllCommentaries(2)),
                Arguments.of("/statistics/stages/77", (ListCall) a -> a.getStatsByStage(77L, 2)),
                Arguments.of("/transfers/latest", (ListCall) a -> a.getLatestTransfers(2)),
                Arguments.of("/transfers/between/2026-07-01/2026-07-31", (ListCall) a -> a.getTransfersByDateRange("2026-07-01", "2026-07-31", 2)),
                Arguments.of("/coaches/search/garcia", (ListCall) a -> a.searchCoaches("garcia", 2)),
                Arguments.of("/referees/search/garcia", (ListCall) a -> a.searchReferees("garcia", 2)),
                Arguments.of("/referees/seasons/13133", (ListCall) a -> a.getRefereesBySeason(13133L, 2)),
                Arguments.of("/standings", (ListCall) a -> a.getAllStandings(2)),
                Arguments.of("/fixtures", (ListCall) a -> a.getAllFixtures(2)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("unlimited")
    void walksEveryPageKeepingIncludes(String path, ListCall call) throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_ids_page1.json");
        enqueue("paged_ids_page2.json");

        List<?> result = call.call(api);

        assertEquals(3, result.size(), "the walk stopped after the first page");
        HttpUrl first = server.takeRequest().getRequestUrl();
        HttpUrl second = server.takeRequest().getRequestUrl();
        assertEquals(path, first.encodedPath());
        assertEquals(path, second.encodedPath());
        assertEquals("NEXT", second.queryParameter("cursor"));
        assertEquals("country", second.queryParameter("include"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("limited")
    void limitIsSentAsPerPageAndStopsTheWalk(String path, ListCall call) throws IOException, SportMonksException, InterruptedException {
        enqueue("paged_ids_page1.json");

        List<?> result = call.call(api);

        assertEquals(2, result.size());
        HttpUrl first = server.takeRequest().getRequestUrl();
        assertEquals(path, first.encodedPath());
        assertEquals("2", first.queryParameter("per_page"));
        assertEquals(1, server.getRequestCount());
    }
}
