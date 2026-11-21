package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.coach.Coach;
import es.com.kete1987.sportmonks.library.football.model.comments.Comment;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.referee.Referee;
import es.com.kete1987.sportmonks.library.football.model.rival.Rival;
import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.squad.SquadPlayer;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.transfer.Transfer;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStation;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;
import es.com.kete1987.sportmonks.library.football.util.EventType;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Audit of the football models against the real API (issue #81). Two kinds of test:
 * the first group exercises the public methods that blew up or lost data; the second compares the raw
 * JSON of each entity (with its includes) against the POJO and fails while any key is not modelled,
 * because Gson drops unknown fields without a trace.
 *
 * Skipped unless {@code SPORTMONKS_API_KEY} is set; run with {@code mvn test -Pintegration}.
 */
class FootballModelsIntegrationTest {
    private static String key;
    private static SportMonksAPI api;

    @BeforeAll
    static void setUp() {
        key = System.getenv("SPORTMONKS_API_KEY");
        Assumptions.assumeTrue(key != null && !key.isEmpty(), "SPORTMONKS_API_KEY not set");
        api = new SportMonksAPI(key);
    }

    private static void assertCovered(String path, Class<?> model) throws IOException, InterruptedException {
        JsonElement data = JsonCoverage.fetchData(key, path);
        List<String> missing = JsonCoverage.missing(data, model);
        assertTrue(missing.isEmpty(), model.getSimpleName() + " does not model: " + missing);
    }

    // ---- public methods that threw or returned nulls ----

    @Test
    void venuesOfAPremierLeagueSeasonDeserializeWithAlphanumericZipcodes() throws IOException, SportMonksException {
        List<Venue> venues = api.getVenues("28083");

        assertFalse(venues.isEmpty());
        assertTrue(venues.stream().anyMatch(v -> v.getZipcode() != null
                && v.getZipcode().chars().anyMatch(Character::isLetter)), "some UK venue has a letter in its zipcode");
    }

    @Test
    void leagueLastPlayedDateArrives() throws IOException, SportMonksException {
        League league = api.getLeagueById(8);

        assertNotNull(league.getLastPlayedAt(), "last_played_at must be read");
    }

    @Test
    void squadWithPlayerIncludeCarriesThePlayer() throws IOException, SportMonksException {
        List<SquadPlayer> squad = api.getSquadByTeam(83, "player");

        assertFalse(squad.isEmpty());
        assertTrue(squad.stream().allMatch(s -> s.getPlayer() != null), "every squad entry has its player");
        assertTrue(squad.stream().allMatch(s -> s.getId() != null && s.getDetailedPositionId() != null));
    }

    @Test
    void eventTypeConstantsExistInTheCatalogAndCoverTheCommonTimelineEvents() throws IOException, SportMonksException,
            IllegalAccessException {
        Map<Long, es.com.kete1987.sportmonks.library.core.model.type.Type> byId = new HashMap<>();
        for (es.com.kete1987.sportmonks.library.core.model.type.Type type : api.getAllTypes()) {
            byId.put(type.getId(), type);
        }
        Map<Long, String> constants = new HashMap<>();
        for (Field f : EventType.class.getFields()) {
            if (f.getType() == int.class) {
                constants.put((long) f.getInt(null), f.getName());
            }
        }

        List<String> problems = new ArrayList<>();
        for (long id : new long[]{126, 568, 1514, 569, 570, 48995}) {
            if (!constants.containsKey(id)) {
                problems.add("no constant for " + id + " (" + byId.get(id).getDeveloperName() + ")");
            }
        }
        constants.forEach((id, name) -> {
            es.com.kete1987.sportmonks.library.core.model.type.Type type = byId.get(id);
            String expected = type == null ? "?" : type.getDeveloperName().replace("_", "");
            if (type == null || !name.replace("_SUB_EVENT", "").replace("_", "").equalsIgnoreCase(expected)) {
                problems.add(name + " (" + id + ") vs " + (type == null ? "missing" : type.getDeveloperName()));
            }
        });

        assertTrue(problems.isEmpty(), "EventType out of sync with core/types: " + problems);
    }

    // ---- raw JSON vs POJO ----

    @Test
    void venueModelsEveryField() throws IOException, InterruptedException {
        assertCovered("football/venues/seasons/28083", Venue.class);
    }

    @Test
    void leagueModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/leagues/8?include=latest;upcoming;stages;sport;country;currentSeason", League.class);
    }

    @Test
    void squadPlayerModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/squads/teams/83?include=player;position;detailedPosition;team", SquadPlayer.class);
    }

    @Test
    void teamModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/teams/83?include=coaches;country;rankings;rivals;socials;sport;statistics;trophies;players",
                Team.class);
    }

    @Test
    void playerModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/players/580?include=city;country;nationality;position;detailedPosition;teams;"
                + "transfers;pendingTransfers;statistics;metadata;trophies;sport", Player.class);
    }

    @Test
    void seasonModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/seasons/23614?include=currentStage;groups;league;statistics;teams;sport", SeasonData.class);
    }

    @Test
    void seasonGroupsOfACupModelEveryField() throws IOException, InterruptedException {
        assertCovered("football/seasons/26618?include=groups", SeasonData.class);
    }

    @Test
    void stageModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/stages/77457866?include=type;rounds;currentRound;league;season", Stage.class);
    }

    @Test
    void roundModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/rounds/274719?include=league;season;stage;statistics", Round.class);
    }

    @Test
    void transferModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/transfers/latest?include=player;fromTeam;toTeam;type;position;detailedPosition",
                Transfer.class);
    }

    @Test
    void coachModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/coaches/search/guardiola?include=country;nationality;teams;statistics", Coach.class);
    }

    @Test
    void refereeModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/referees/seasons/23614?include=city;country;nationality;statistics", Referee.class);
    }

    @Test
    void rivalModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/rivals/teams/83?include=rival;team", Rival.class);
    }

    @Test
    void commentModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/commentaries/fixtures/19722106?include=fixture;player;relatedPlayer", Comment.class);
    }

    @Test
    void tvStationModelsEveryFieldAndInclude() throws IOException, InterruptedException {
        assertCovered("football/tv-stations?include=countries", TvStation.class);
    }
}
