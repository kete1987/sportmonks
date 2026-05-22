package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeasonApiTest extends BaseApiTest {

    @Test
    void getSeasons_returnsParsedList() throws IOException, SportMonksException {
        enqueue("seasons.json");

        List<SeasonData> seasons = api.getSeasons();

        assertEquals(2, seasons.size());
        assertEquals(21646, seasons.get(0).getId().intValue());
        assertEquals("2023/2024", seasons.get(0).getName());
        assertTrue(seasons.get(0).getFinished());
        assertEquals(23614, seasons.get(1).getId().intValue());
        assertTrue(seasons.get(1).getIsCurrent());
    }
}
