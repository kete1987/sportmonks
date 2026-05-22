package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.comments.Comment;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentaryApiTest extends BaseApiTest {

    @Test
    void getAllCommentaries_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("commentaries.json");

        api.getAllCommentaries();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("commentaries"));
        assertFalse(request.getPath().contains("commentaries/"));
    }

    @Test
    void getAllCommentaries_returnsParsedList() throws IOException, SportMonksException {
        enqueue("commentaries.json");

        List<Comment> comments = api.getAllCommentaries();

        assertEquals(2, comments.size());
        assertEquals(5001L, comments.get(0).getId());
        assertEquals(1001L, comments.get(0).getFixtureId());
        assertEquals("The match has kicked off!", comments.get(0).getComment());
        assertTrue(comments.get(0).isImportant());
        assertFalse(comments.get(0).isGoal());
    }

    @Test
    void getCommentaryByFixture_usesFixturePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("commentaries.json");

        api.getCommentaryByFixture(1001L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("commentaries/fixtures/1001"));
    }

    @Test
    void getCommentaryByFixture_returnsParsedList() throws IOException, SportMonksException {
        enqueue("commentaries.json");

        List<Comment> comments = api.getCommentaryByFixture(1001L);

        assertEquals(2, comments.size());
        assertEquals(5002L, comments.get(1).getId());
        assertTrue(comments.get(1).isGoal());
    }
}
