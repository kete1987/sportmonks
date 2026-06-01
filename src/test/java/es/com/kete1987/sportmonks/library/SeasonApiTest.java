package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.season.KnockoutBracket;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import okhttp3.mockwebserver.RecordedRequest;
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
        SeasonData first = seasons.get(0);
        assertEquals(21646, first.getId().intValue());
        assertEquals("2023/2024", first.getName());
        assertTrue(first.getFinished());
        assertFalse(first.getPending());
        assertFalse(first.getIsCurrent());
        assertEquals(1L, first.getSportId());
        assertEquals(8L, first.getLeagueId());

        SeasonData second = seasons.get(1);
        assertEquals(23614, second.getId().intValue());
        assertTrue(second.getIsCurrent());
        assertFalse(second.getPending());
    }

    @Test
    void getSeasonById_returnsParsed() throws IOException, SportMonksException {
        enqueue("season_detail.json");

        SeasonData season = api.getSeasonById(21646L);

        assertNotNull(season);
        assertEquals(21646L, season.getId());
        assertEquals("2023/2024", season.getName());
        assertTrue(season.getFinished());
    }

    @Test
    void getSeasonById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("season_detail.json");

        api.getSeasonById(21646L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("seasons/21646"));
    }

    @Test
    void getSeasonsByTeam_usesTeamPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("seasons.json");

        api.getSeasonsByTeam(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("seasons/teams/1"));
    }

    @Test
    void getSeasonsByTeam_returnsParsedList() throws IOException, SportMonksException {
        enqueue("seasons.json");

        List<SeasonData> seasons = api.getSeasonsByTeam(1L);

        assertEquals(2, seasons.size());
    }

    @Test
    void searchSeasons_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("seasons.json");

        api.searchSeasons("2023");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("seasons/search/2023"));
    }

    @Test
    void searchSeasons_returnsParsedList() throws IOException, SportMonksException {
        enqueue("seasons.json");

        List<SeasonData> seasons = api.searchSeasons("2023");

        assertEquals(2, seasons.size());
    }

    @Test
    void getBracketsBySeason_usesBracketsPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("brackets.json");

        api.getBracketsBySeason(23614L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("seasons/23614/brackets"));
    }

    @Test
    void getBracketsBySeason_returnsParsedKnockout() throws IOException, SportMonksException {
        enqueue("brackets.json");

        KnockoutBracket bracket = api.getBracketsBySeason(26618L);

        assertNotNull(bracket.getStages());
        assertEquals(2, bracket.getStages().size());
        assertEquals("16th Finals", bracket.getStages().get(0).getStageName());
        assertEquals(77479086L, bracket.getStages().get(0).getStageId());
        assertEquals(1, bracket.getStages().get(0).getFixtures().size());
        assertTrue(bracket.getStages().get(0).getFixtures().get(0).getPlaceholder());
        assertEquals("2nd position Group A vs 2nd position Group B",
                bracket.getStages().get(0).getFixtures().get(0).getName());

        assertNotNull(bracket.getEdges());
        assertEquals(2, bracket.getEdges().size());
        assertEquals(19606961L, bracket.getEdges().get(0).getChildFixtureId());
        assertEquals("home", bracket.getEdges().get(0).getChildSlot());
        assertEquals(19606960L, bracket.getEdges().get(0).getParentFixtureId());
        assertEquals("winner", bracket.getEdges().get(0).getParentOutcome());
    }
}
