package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.rival.Rival;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RivalApiTest extends BaseApiTest {

    @Test
    void getAllRivals_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("rivals.json");

        api.getAllRivals();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("rivals"));
        assertFalse(request.getPath().contains("rivals/"));
    }

    @Test
    void getAllRivals_returnsParsedList() throws IOException, SportMonksException {
        enqueue("rivals.json");

        List<Rival> rivals = api.getAllRivals();

        assertEquals(2, rivals.size());
        assertEquals(4001L, rivals.get(0).getId());
        assertEquals(1L, rivals.get(0).getTeamId());
        assertEquals(2L, rivals.get(0).getRivalId());
    }

    @Test
    void getRivalsByTeam_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("rivals.json");

        api.getRivalsByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("rivals/teams/1"));
    }

    @Test
    void getRivalsByTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("rivals.json");

        List<Rival> rivals = api.getRivalsByTeam(1L);

        assertEquals(2, rivals.size());
    }
}
