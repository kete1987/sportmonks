package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.transfer.TransferRumour;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransferRumourApiTest extends BaseApiTest {

    @Test
    void getAllTransferRumours_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfer_rumours.json");

        api.getAllTransferRumours();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfer-rumours"));
        assertFalse(request.getPath().contains("transfer-rumours/"));
    }

    @Test
    void getAllTransferRumours_returnsParsedList() throws IOException, SportMonksException {
        enqueue("transfer_rumours.json");

        List<TransferRumour> rumours = api.getAllTransferRumours();

        assertEquals(2, rumours.size());
        assertEquals(3001L, rumours.get(0).getId());
        assertEquals(101L, rumours.get(0).getPlayerId());
        assertEquals(1L, rumours.get(0).getFromTeamId());
        assertEquals(5L, rumours.get(0).getToTeamId());
        assertEquals("2024-01-01", rumours.get(0).getStartDate());
    }

    @Test
    void getTransferRumourById_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfer_rumour_detail.json");

        api.getTransferRumourById(3001L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfer-rumours/3001"));
    }

    @Test
    void getTransferRumourById_returnsParsed() throws IOException, SportMonksException {
        enqueue("transfer_rumour_detail.json");

        TransferRumour rumour = api.getTransferRumourById(3001L);

        assertNotNull(rumour);
        assertEquals(3001L, rumour.getId());
    }

    @Test
    void getTransferRumoursByDateRange_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfer_rumours.json");

        api.getTransferRumoursByDateRange("2024-01-01", "2024-06-30");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfer-rumours/between/2024-01-01/2024-06-30"));
    }

    @Test
    void getTransferRumoursByTeam_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfer_rumours.json");

        api.getTransferRumoursByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfer-rumours/teams/1"));
    }

    @Test
    void getTransferRumoursByPlayer_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfer_rumours.json");

        api.getTransferRumoursByPlayer(101L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfer-rumours/players/101"));
    }
}
