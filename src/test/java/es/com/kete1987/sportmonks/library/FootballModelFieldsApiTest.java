package es.com.kete1987.sportmonks.library;

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
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Fixtures trimmed from real responses (issue #81): fields and includes that Gson used to drop, and
 * values it could not parse. The fixture names say which response they come from.
 */
class FootballModelFieldsApiTest extends BaseApiTest {

    @Test
    void venues_keepAlphanumericZipcodesAndTheNewFields() throws IOException, SportMonksException {
        enqueue("venues_uk_zipcode.json");

        List<Venue> venues = api.getVenues("28083");

        assertEquals(3, venues.size());
        Venue coventry = venues.get(0);
        assertEquals("CV6 6GE", coventry.getZipcode());
        assertEquals("52.44836", coventry.getLatitude());
        assertEquals("-1.49664", coventry.getLongitude());
        assertEquals("grass", coventry.getSurface());
        assertEquals(Boolean.FALSE, coventry.getNationalTeam());
        assertEquals("Coventry", coventry.getCityName());
    }

    @Test
    void venues_stillReadNumericZipcodesAsStrings() throws IOException, SportMonksException {
        enqueue("venues_uk_zipcode.json");

        Venue numeric = api.getVenues("28083").get(2);

        assertTrue(numeric.getZipcode().matches("\\d+"), numeric.getZipcode());
    }

    @Test
    void league_readsLastPlayedAtAndTheDeprecatedGetterDelegates() throws IOException, SportMonksException {
        enqueue("league_with_includes.json");

        League league = api.getLeagueById(8);

        assertEquals("2026-11-08 16:30:00", league.getLastPlayedAt());
        assertEquals(Long.valueOf(1), league.getCategory());
    }

    @Test
    void league_exposesLatestUpcomingStagesAndSport() throws IOException, SportMonksException {
        enqueue("league_with_includes.json");

        League league = api.getLeagueById(8, "latest", "upcoming", "stages", "sport", "country");

        assertEquals(2, league.getLatest().size());
        assertEquals(Long.valueOf(19722106), league.getLatest().get(0).getId());
        assertFalse(league.getUpcoming().isEmpty());
        assertFalse(league.getStages().isEmpty());
        assertEquals("football", league.getSport().getCode());
        assertEquals("ENG", league.getCountry().getIso3());
        assertNotNull(league.getCountry().getBorders());
    }

    @Test
    void league_withoutIncludesLeavesThemNull() throws IOException, SportMonksException {
        enqueue("leagues.json");

        League league = api.getAllLeagues().get(0);

        assertNull(league.getLatest());
        assertNull(league.getStages());
        assertNull(league.getSport());
        assertNull(league.getLastPlayedAt());
    }

    @Test
    void squad_carriesIdDetailedPositionAndIncludes() throws IOException, SportMonksException {
        enqueue("squad_with_includes.json");

        List<SquadPlayer> squad = api.getSquadByTeam(83, "player", "position", "detailedPosition", "team");

        assertEquals(2, squad.size());
        SquadPlayer first = squad.get(0);
        assertEquals(Long.valueOf(1281178), first.getId());
        assertEquals(Long.valueOf(156), first.getDetailedPositionId());
        assertEquals("Roony Bardghji", first.getPlayer().getName());
        assertEquals("Attacker", first.getPosition().getName());
        assertEquals("Right Wing", first.getDetailedPosition().getName());
        assertEquals("FC Barcelona", first.getTeam().getName());
        assertNull(first.getTransfer());
    }

    @Test
    void team_exposesTheIncludes() throws IOException, SportMonksException {
        enqueue("team_with_includes.json");

        Team team = api.getTeamById(83, "coaches", "country", "rankings", "rivals", "socials", "sport");

        assertEquals(2, team.getCoaches().size());
        assertEquals(Long.valueOf(184942), team.getCoaches().get(0).getCoachId());
        assertEquals("Spain", team.getCountry().getName());
        assertEquals("UEFA", team.getRankings().get(0).getType());
        assertEquals("Atlético de Madrid", team.getRivals().get(0).getName());
        assertEquals("@FCBarcelona", team.getSocials().get(0).getValue());
        assertEquals("Football", team.getSport().getName());
    }

    @Test
    void team_exposesStatisticsTrophiesAndCaptain() throws IOException, SportMonksException {
        enqueue("team_with_includes.json");

        Team team = api.getTeamById(83, "statistics", "trophies", "players");

        assertEquals(Long.valueOf(718), team.getStatistics().get(0).getSeasonId());
        assertEquals(Boolean.TRUE, team.getStatistics().get(0).getHasValues());
        assertEquals(Long.valueOf(630), team.getTrophies().get(0).getId());
        assertEquals(Long.valueOf(570), team.getTrophies().get(0).getLeagueId());
        assertEquals(Boolean.FALSE, team.getPlayers().get(0).getCaptain());
    }

    @Test
    void player_exposesTheIncludes() throws IOException, SportMonksException {
        enqueue("player_with_includes.json");

        Player player = api.getPlayerById(580, "city", "country", "nationality", "position", "detailedPosition");

        assertEquals("Funchal", player.getCity().getName());
        assertEquals("Portugal", player.getCountry().getName());
        assertEquals("PRT", player.getNationality().getIso3());
        assertEquals("Attacker", player.getPosition().getName());
        assertEquals("Centre Forward", player.getDetailedPosition().getName());
        assertEquals("football", player.getSport().getCode());
    }

    @Test
    void player_exposesTeamsTransfersStatisticsMetadataAndTrophies() throws IOException, SportMonksException {
        enqueue("player_with_includes.json");

        Player player = api.getPlayerById(580, "teams", "transfers", "pendingTransfers", "statistics", "metadata", "trophies");

        assertEquals(Boolean.TRUE, player.getTeams().get(0).getCaptain());
        assertEquals(Long.valueOf(19000000), player.getTransfers().get(0).getAmount());
        assertTrue(player.getPendingTransfers().isEmpty());
        assertEquals(Long.valueOf(7), player.getStatistics().get(0).getJerseyNumber());
        assertEquals(Long.valueOf(580), player.getStatistics().get(0).getPlayerId());
        assertEquals(Long.valueOf(229), player.getMetadata().get(0).getTypeId());
        assertEquals(Long.valueOf(79870), player.getTrophies().get(0).getId());
    }

    @Test
    void season_exposesTheIncludes() throws IOException, SportMonksException {
        enqueue("season_with_includes.json");

        SeasonData season = api.getSeasonById(23614, "league", "statistics", "teams", "topscorers", "sport");

        assertEquals("Premier League", season.getLeague().getName());
        assertEquals(Long.valueOf(34), season.getStatistics().get(0).getTypeId());
        assertEquals("array", season.getStatistics().get(0).getType());
        assertEquals(2, season.getTeams().size());
        assertEquals(Long.valueOf(165), season.getTopscorers().get(0).getPlayerId());
        assertEquals("Football", season.getSport().getName());
        assertNull(season.getCurrentStage());
        assertTrue(season.getGroups().isEmpty());
    }

    @Test
    void season_exposesTheGroupsOfACup() throws IOException, SportMonksException {
        enqueue("season_groups.json");

        SeasonData season = api.getSeasonById(26618, "groups");

        assertEquals("Group A", season.getGroups().get(0).getName());
        assertEquals(Boolean.TRUE, season.getGroups().get(0).getIsFinished());
    }

    @Test
    void stage_exposesTieBreakerTypeRoundsAndRelations() throws IOException, SportMonksException {
        enqueue("stage_with_includes.json");

        Stage stage = api.getStageById(77457866L, "type", "rounds", "currentRound", "league", "season");

        assertEquals(Long.valueOf(171), stage.getTieBreakerRuleId());
        assertEquals("GROUP_STAGE", stage.getType().getDeveloperName());
        assertEquals(2, stage.getRounds().size());
        assertNull(stage.getCurrentRound());
        assertEquals("Premiership", stage.getLeague().getName());
        assertEquals("2022/2023", stage.getSeason().getName());
    }

    @Test
    void stage_gamesInCurrentWeekComesAsABoolean() throws IOException, SportMonksException {
        enqueue("stage_with_includes.json");

        Stage stage = api.getStageById(77457866L);

        assertEquals(Boolean.FALSE, stage.isGamesInCurrentWeek());
        assertEquals("false", stage.getGamesInCurrentWeek());
    }

    @Test
    void round_exposesLeagueSeasonStageAndStatistics() throws IOException, SportMonksException {
        enqueue("round_with_includes.json");

        Round round = api.getRoundById(274719L, "league", "season", "stage", "statistics");

        assertEquals("Premiership", round.getLeague().getName());
        assertEquals("2022/2023", round.getSeason().getName());
        assertEquals("1st Phase", round.getStage().getName());
        assertEquals(2, round.getStatistics().size());
        assertEquals(Long.valueOf(274719), round.getStatistics().get(0).getModelId());
    }

    @Test
    void transfer_exposesTheIncludes() throws IOException, SportMonksException {
        enqueue("transfers_with_includes.json");

        List<Transfer> transfers = api.getLatestTransfers("player", "fromTeam", "toTeam", "type", "position", "detailedPosition");

        Transfer transfer = transfers.get(0);
        assertEquals(Long.valueOf(1), transfer.getSportId());
        assertEquals(Long.valueOf(148), transfer.getDetailedPositionId());
        assertEquals("Cole McGhee", transfer.getPlayer().getName());
        assertEquals("Sheffield Wednesday", transfer.getFromTeam().getName());
        assertEquals("Torquay United", transfer.getToTeam().getName());
        assertEquals("LOAN_TRANSFER", transfer.getType().getDeveloperName());
        assertEquals("Defender", transfer.getPosition().getName());
        assertEquals("Centre Back", transfer.getDetailedPosition().getName());
    }

    @Test
    void coach_exposesTheNewFieldsAndIncludes() throws IOException, SportMonksException {
        enqueue("coaches_with_includes.json");

        Coach coach = api.searchCoaches("guardiola", "country", "nationality", "teams", "statistics").get(0);

        assertEquals(Long.valueOf(455361), coach.getPlayerId());
        assertEquals(Long.valueOf(180), coach.getHeight());
        assertEquals(Long.valueOf(70), coach.getWeight());
        assertNull(coach.getCityId());
        assertEquals("Spain", coach.getCountry().getName());
        assertEquals("ESP", coach.getNationality().getIso3());
        assertEquals(Long.valueOf(9), coach.getTeams().get(0).getTeamId());
        assertEquals(Long.valueOf(455361), coach.getStatistics().get(0).getCoachId());
    }

    @Test
    void referee_exposesTheNewFieldsAndIncludes() throws IOException, SportMonksException {
        enqueue("referees_with_includes.json");

        Referee referee = api.getRefereesBySeason(23614, "country", "nationality", "statistics").get(0);

        assertEquals("D. Coote", referee.getCommonName());
        assertEquals("David", referee.getFirstname());
        assertEquals("Coote", referee.getLastname());
        assertNull(referee.getHeight());
        assertNull(referee.getWeight());
        assertNull(referee.getCity());
        assertEquals("England", referee.getCountry().getName());
        assertEquals("ENG", referee.getNationality().getIso3());
        assertEquals(Long.valueOf(11691), referee.getStatistics().get(0).getRefereeId());
    }

    @Test
    void rival_exposesRivalAndTeam() throws IOException, SportMonksException {
        enqueue("rivals_with_includes.json");

        Rival rival = api.getRivalsByTeam(83, "rival", "team").get(0);

        assertEquals("ATM", rival.getRival().getShortCode());
        assertEquals("FC Barcelona", rival.getTeam().getName());
    }

    @Test
    void comment_exposesFixtureAndPlayers() throws IOException, SportMonksException {
        enqueue("commentaries_with_includes.json");

        Comment comment = api.getCommentaryByFixture(19722106L, "fixture", "player", "relatedPlayer").get(0);

        assertEquals(Long.valueOf(19722106), comment.getFixture().getId());
        assertNull(comment.getPlayer());
        assertNull(comment.getRelatedPlayer());
    }

    @Test
    void tvStation_exposesTypeRelatedIdAndCountries() throws IOException, SportMonksException {
        enqueue("tvstations_with_countries.json");

        TvStation station = api.getAllTvStations("countries").get(0);

        assertEquals("tv", station.getType());
        assertNull(station.getRelatedId());
        assertEquals(2, station.getCountries().size());
        assertEquals("Brazil", station.getCountries().get(0).getName());
    }

    @Test
    void eventType_hasTheTimelineEventIds() {
        assertEquals(126, EventType.CORNER);
        assertEquals(568, EventType.OFFSIDE);
        assertEquals(1514, EventType.OFFSIDE_SUB_EVENT);
        assertEquals(569, EventType.SHOT_ON_TARGET);
        assertEquals(570, EventType.SHOT_OFF_TARGET);
        assertEquals(48995, EventType.WOODWORK);
    }
}
