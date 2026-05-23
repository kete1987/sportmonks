package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.transfer.Transfer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransferApiTest extends BaseApiTest {

    @Test
    void getAllTransfers_usesTransfersPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfers.json");

        api.getAllTransfers();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfers"));
        assertFalse(request.getPath().contains("transfers/"));
    }

    @Test
    void getAllTransfers_returnsParsedList() throws IOException, SportMonksException {
        enqueue("transfers.json");

        List<Transfer> transfers = api.getAllTransfers();

        assertEquals(2, transfers.size());
        assertEquals(2001L, transfers.get(0).getId());
        assertEquals(101L, transfers.get(0).getPlayerId());
        assertEquals(1L, transfers.get(0).getFromTeamId());
        assertEquals(2L, transfers.get(0).getToTeamId());
        assertEquals(50000000L, transfers.get(0).getAmount());
        assertTrue(transfers.get(0).getCompleted());
    }

    @Test
    void getTransferById_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfer_detail.json");

        api.getTransferById(2001L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfers/2001"));
    }

    @Test
    void getTransferById_returnsParsed() throws IOException, SportMonksException {
        enqueue("transfer_detail.json");

        Transfer transfer = api.getTransferById(2001L);

        assertNotNull(transfer);
        assertEquals(2001L, transfer.getId());
        assertEquals("2023-07-01", transfer.getDate());
    }

    @Test
    void getLatestTransfers_usesLatestPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfers.json");

        api.getLatestTransfers();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfers/latest"));
    }

    @Test
    void getTransfersByDateRange_usesDateRangePath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfers.json");

        api.getTransfersByDateRange("2023-07-01", "2023-07-31");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfers/between/2023-07-01/2023-07-31"));
    }

    @Test
    void getTransfersByTeam_usesTeamPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfers.json");

        api.getTransfersByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfers/teams/1"));
    }

    @Test
    void getTransfersByPlayer_usesPlayerPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("transfers.json");

        api.getTransfersByPlayer(101L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("transfers/players/101"));
    }
}
