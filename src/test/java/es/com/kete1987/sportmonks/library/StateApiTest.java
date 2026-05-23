package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.match.State;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateApiTest extends BaseApiTest {

    @Test
    void getAllStates_usesStatesPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("states.json");

        api.getAllStates();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("states"));
        assertFalse(request.getPath().contains("states/"));
    }

    @Test
    void getAllStates_returnsParsedList() throws IOException, SportMonksException {
        enqueue("states.json");

        List<State> states = api.getAllStates();

        assertEquals(2, states.size());
        assertEquals(1L, states.get(0).getId());
        assertEquals("NS", states.get(0).getState());
        assertEquals("Not Started", states.get(0).getName());
        assertEquals("NS", states.get(0).getShortName());
        assertEquals("NOT_STARTED", states.get(0).getDeveloperName());
        assertEquals(2L, states.get(1).getId());
        assertEquals("LIVE", states.get(1).getState());
    }

    @Test
    void getStateById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("state_detail.json");

        api.getStateById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("states/1"));
    }

    @Test
    void getStateById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("state_detail.json");

        State state = api.getStateById(1L);

        assertNotNull(state);
        assertEquals(1L, state.getId());
        assertEquals("NS", state.getState());
        assertEquals("Not Started", state.getName());
        assertEquals("NOT_STARTED", state.getDeveloperName());
    }
}
