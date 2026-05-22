package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerApiTest extends BaseApiTest {

    @Test
    void getAllPlayers_usesPlayersPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("players.json");

        api.getAllPlayers();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("players"));
        assertFalse(request.getPath().contains("players/"));
    }

    @Test
    void getAllPlayers_returnsParsedList() throws IOException, SportMonksException {
        enqueue("players.json");

        List<Player> players = api.getAllPlayers();

        assertEquals(2, players.size());
        assertEquals(100L, players.get(0).getId());
        assertEquals("Marcus Rashford", players.get(0).getName());
        assertEquals("Marcus", players.get(0).getFirstname());
        assertEquals("Rashford", players.get(0).getLastname());
        assertEquals("M. Rashford", players.get(0).getDisplayName());
        assertEquals("male", players.get(0).getGender());
    }

    @Test
    void getPlayerById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("player_detail.json");

        api.getPlayerById(100L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("players/100"));
    }

    @Test
    void getPlayerById_returnsParsed() throws IOException, SportMonksException {
        enqueue("player_detail.json");

        Player player = api.getPlayerById(100L);

        assertNotNull(player);
        assertEquals(100L, player.getId());
        assertEquals("Marcus Rashford", player.getName());
    }

    @Test
    void getPlayersByCountry_usesCountryPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("players.json");

        api.getPlayersByCountry(462L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("players/countries/462"));
    }

    @Test
    void getPlayersByCountry_returnsParsedList() throws IOException, SportMonksException {
        enqueue("players.json");

        List<Player> players = api.getPlayersByCountry(462L);

        assertEquals(2, players.size());
    }

    @Test
    void searchPlayers_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("players.json");

        api.searchPlayers("Rashford");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("players/search/Rashford"));
    }

    @Test
    void searchPlayers_returnsParsedList() throws IOException, SportMonksException {
        enqueue("players.json");

        List<Player> players = api.searchPlayers("Rashford");

        assertEquals(2, players.size());
    }

    @Test
    void getLatestUpdatedPlayers_usesLatestPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("players.json");

        api.getLatestUpdatedPlayers();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("players/latest"));
    }

    @Test
    void getLatestUpdatedPlayers_returnsParsedList() throws IOException, SportMonksException {
        enqueue("players.json");

        List<Player> players = api.getLatestUpdatedPlayers();

        assertEquals(2, players.size());
    }
}
