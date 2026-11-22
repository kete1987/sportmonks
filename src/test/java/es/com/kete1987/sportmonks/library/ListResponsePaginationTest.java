package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.football.model.comments.CommentsResponse;
import es.com.kete1987.sportmonks.library.football.model.rival.RivalsResponse;
import es.com.kete1987.sportmonks.library.football.model.venue.VenueResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Issue #82: the venue, rival and commentary list responses expose their {@code pagination},
 * so {@code FootballApi} walks them through their public {@code *Response} classes instead of
 * private page envelopes.
 */
class ListResponsePaginationTest extends BaseApiTest {

    private static final String PAGE = "{\"data\":[],\"pagination\":{\"count\":25,\"per_page\":25,"
            + "\"current_page\":3,\"has_more\":true,\"next_page\":null,\"next_cursor\":null}}";

    private <R> R parse(Class<R> type) {
        return api.getFootball().gson().fromJson(PAGE, type);
    }

    private static void assertPage(Pagination pagination) {
        assertEquals(Long.valueOf(3), pagination.getCurrentPage());
        assertTrue(pagination.hasMore());
    }

    @Test
    void venueResponse_hasPagination() {
        assertPage(parse(VenueResponse.class).getPagination());
    }

    @Test
    void rivalsResponse_hasPagination() {
        assertPage(parse(RivalsResponse.class).getPagination());
    }

    @Test
    void commentsResponse_hasPagination() {
        assertPage(parse(CommentsResponse.class).getPagination());
    }
}
