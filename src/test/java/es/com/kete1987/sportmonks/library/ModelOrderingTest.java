package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.com.kete1987.sportmonks.library.football.model.comments.Comment;
import es.com.kete1987.sportmonks.library.football.model.match.EventData;
import es.com.kete1987.sportmonks.library.football.model.match.Match;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.util.EventType;
import es.com.kete1987.sportmonks.library.football.util.MatchStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ordering of {@link Match}, {@link Comment}, {@link Stage} and {@link EventData} (issue #80): the
 * order must be total, antisymmetric, transitive and consistent with {@code equals}, also with null
 * keys mixed in, because {@code sorted()}, {@code TreeSet} and {@code Collections.sort} rely on it.
 */
class ModelOrderingTest {
    private static final Gson GSON = new FootballApi("test-token").gson();
    private static final Long[] STATES = {null, (long) MatchStatus.NOT_STARTED, (long) MatchStatus.INPLAY_1ST_HALF,
            (long) MatchStatus.HT, (long) MatchStatus.FT, (long) MatchStatus.POSTPONED};
    private static final Long[] STAMPS = {null, 100L, 200L, 300L};
    private static final Long[] ORDERS = {null, 1L, 2L};
    private static final String[] DATES = {null, "2026-01-01", "2026-02-01"};
    private static final Long[] EVENT_TYPES = {null, (long) EventType.PENALTY_SHOOTOUT_GOAL, (long) EventType.GOAL};
    private static final Long[] MINUTES = {null, 1L, 45L, 90L};
    private static final Long[] EXTRA_MINUTES = {null, 0L, 2L};

    private static Match match(Long id, Long stateId, Long timestamp) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("state_id", stateId);
        json.addProperty("starting_at_timestamp", timestamp);
        return GSON.fromJson(json, Match.class);
    }

    private static Comment comment(Long id, Long order) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("order", order);
        return GSON.fromJson(json, Comment.class);
    }

    private static Stage stage(Long id, Long sortOrder, String endingAt) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("sort_order", sortOrder);
        json.addProperty("ending_at", endingAt);
        return GSON.fromJson(json, Stage.class);
    }

    private static EventData event(Long id, Long typeId, Long minute, Long extraMinute) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("type_id", typeId);
        json.addProperty("minute", minute);
        json.addProperty("extra_minute", extraMinute);
        return GSON.fromJson(json, EventData.class);
    }

    private static <T> T pick(Random random, T[] values) {
        return values[random.nextInt(values.length)];
    }

    /** A unique id, or null one time in four. */
    private static Long idOrNull(Random random, int counter) {
        return random.nextInt(4) == 0 ? null : (long) counter + 1;
    }

    private static List<Match> randomMatches(Random random, int size) {
        List<Match> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(match(idOrNull(random, i), pick(random, STATES), pick(random, STAMPS)));
        }
        return list;
    }

    private static List<Comment> randomComments(Random random, int size) {
        List<Comment> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(comment(idOrNull(random, i), pick(random, ORDERS)));
        }
        return list;
    }

    private static List<Stage> randomStages(Random random, int size) {
        List<Stage> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(stage(idOrNull(random, i), pick(random, ORDERS), pick(random, DATES)));
        }
        return list;
    }

    private static List<EventData> randomEvents(Random random, int size) {
        List<EventData> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(event(idOrNull(random, i), pick(random, EVENT_TYPES), pick(random, MINUTES),
                    pick(random, EXTRA_MINUTES)));
        }
        return list;
    }

    // ---- Match ----

    @Test
    void match_stateGroupsKeepLiveThenNotStartedThenFinishedThenOther() {
        Match live = match(1L, (long) MatchStatus.INPLAY_2ND_HALF, 400L);
        Match notStarted = match(2L, (long) MatchStatus.NOT_STARTED, 400L);
        Match finished = match(3L, (long) MatchStatus.FT, 100L);
        Match other = match(4L, (long) MatchStatus.POSTPONED, 100L);

        List<Match> sorted = new ArrayList<>(List.of(other, finished, notStarted, live));
        sorted.sort(null);

        assertEquals(List.of(live, notStarted, finished, other), sorted);
    }

    @Test
    void match_nullStateCountsAsOtherAndSortsAfterFinished() {
        Match noState = match(1L, null, 50L);
        Match finished = match(2L, (long) MatchStatus.FT, 900L);
        Match live = match(3L, (long) MatchStatus.HT, 900L);

        assertTrue(finished.compareTo(noState) < 0);
        assertTrue(noState.compareTo(finished) > 0);
        assertTrue(live.compareTo(noState) < 0);
        assertTrue(noState.compareTo(live) > 0);
    }

    @Test
    void match_sameStateOrdersByTimestampWithNullsLastThenById() {
        Match early = match(9L, (long) MatchStatus.FT, 100L);
        Match late = match(1L, (long) MatchStatus.FT, 200L);
        Match noStamp = match(2L, (long) MatchStatus.FT, null);
        Match sameStampLowId = match(3L, (long) MatchStatus.FT, 100L);
        Match noId = match(null, (long) MatchStatus.FT, 100L);

        List<Match> sorted = new ArrayList<>(List.of(noStamp, noId, late, early, sameStampLowId));
        sorted.sort(null);

        assertEquals(List.of(sameStampLowId, early, noId, late, noStamp), sorted);
    }

    @Test
    void match_treeSetKeepsDistinctMatches() {
        TreeSet<Match> set = new TreeSet<>();
        set.add(match(1L, (long) MatchStatus.FT, null));
        set.add(match(2L, (long) MatchStatus.FT, null));
        set.add(match(3L, null, null));
        set.add(match(4L, null, null));
        set.add(match(5L, (long) MatchStatus.NOT_STARTED, 100L));
        set.add(match(6L, (long) MatchStatus.NOT_STARTED, 100L));

        assertEquals(6, set.size());
        assertTrue(set.contains(match(2L, (long) MatchStatus.FT, null)));
    }

    @Test
    void match_randomListsWithManyNullsSortWithoutContractViolation() {
        Random random = new Random(80);
        for (int round = 0; round < 200; round++) {
            List<Match> list = randomMatches(random, 120);

            list.sort(null);

            assertTrue(list.get(0).compareTo(list.get(list.size() - 1)) <= 0);
        }
    }

    @Test
    void match_orderingIsAntisymmetricTransitiveAndConsistentWithEquals() {
        Random random = new Random(81);
        for (int round = 0; round < 20; round++) {
            List<String> violations = ComparableContractCheck.violations(randomMatches(random, 40));
            assertTrue(violations.isEmpty(), "Match ordering: " + violations);
        }
    }

    @Test
    void match_withoutIdIsOnlyEqualToItself() {
        Match first = match(null, (long) MatchStatus.FT, 100L);
        Match second = match(null, (long) MatchStatus.FT, 200L);

        assertNotEquals(first, second);
        assertEquals(first, first);
        assertEquals(match(7L, null, null), match(7L, (long) MatchStatus.FT, 5L));
        assertEquals(match(7L, null, null).hashCode(), match(7L, (long) MatchStatus.FT, 5L).hashCode());
    }

    // ---- Comment ----

    @Test
    void comment_orderingIsTotalWithNullsMixedIn() {
        Random random = new Random(82);
        for (int round = 0; round < 20; round++) {
            List<String> violations = ComparableContractCheck.violations(randomComments(random, 40));
            assertTrue(violations.isEmpty(), "Comment ordering: " + violations);
        }
    }

    @Test
    void comment_treeSetKeepsDistinctComments() {
        TreeSet<Comment> set = new TreeSet<>(List.of(comment(1L, 5L), comment(2L, 5L), comment(3L, null),
                comment(4L, null)));

        assertEquals(4, set.size());
    }

    @Test
    void comment_withoutIdIsOnlyEqualToItself() {
        Comment first = comment(null, 1L);
        Comment second = comment(null, 1L);

        assertNotEquals(first, second);
        assertEquals(first, first);
    }

    // ---- Stage ----

    @Test
    void stage_orderingIsTotalWithNullsMixedIn() {
        Random random = new Random(83);
        for (int round = 0; round < 20; round++) {
            List<String> violations = ComparableContractCheck.violations(randomStages(random, 40));
            assertTrue(violations.isEmpty(), "Stage ordering: " + violations);
        }
    }

    @Test
    void stage_treeSetKeepsDistinctStages() {
        TreeSet<Stage> set = new TreeSet<>(List.of(stage(1L, 1L, null), stage(2L, 1L, null), stage(3L, null, null),
                stage(4L, null, null)));

        assertEquals(4, set.size());
    }

    @Test
    void stage_withoutIdIsOnlyEqualToItself() {
        Stage first = stage(null, 1L, null);
        Stage second = stage(null, 1L, null);

        assertNotEquals(first, second);
        assertEquals(first, first);
    }

    // ---- EventData ----

    @Test
    void eventData_orderingIsTotalWithNullsMixedIn() {
        Random random = new Random(84);
        for (int round = 0; round < 20; round++) {
            List<String> violations = ComparableContractCheck.violations(randomEvents(random, 40));
            assertTrue(violations.isEmpty(), "EventData ordering: " + violations);
        }
    }

    @Test
    void eventData_treeSetKeepsDistinctEvents() {
        TreeSet<EventData> set = new TreeSet<>(List.of(event(1L, null, null, null), event(2L, null, null, null),
                event(3L, null, 45L, null), event(4L, null, 45L, 2L)));

        assertEquals(4, set.size());
    }

    @Test
    void eventData_largeIdsDoNotOverflowTheComparison() {
        EventData lower = event(4_000_000_000L, null, 10L, null);
        EventData higher = event(4_000_000_001L, null, 10L, null);
        EventData sameLowBits = event(8_000_000_000L, null, 10L, null);

        assertTrue(lower.compareTo(higher) < 0);
        assertTrue(lower.compareTo(sameLowBits) < 0);
        assertTrue(sameLowBits.compareTo(lower) > 0);
    }

    @Test
    void eventData_withoutIdIsOnlyEqualToItself() {
        EventData first = event(null, null, 10L, null);
        EventData second = event(null, null, 10L, null);

        assertNotEquals(first, second);
        assertEquals(first, first);
        assertEquals(event(5L, null, 1L, null), event(5L, null, 2L, null));
    }

    @Test
    void eventData_minutesOrderBeforeIdAndShootoutGoesLast() {
        EventData early = event(10L, (long) EventType.GOAL, 5L, null);
        EventData stoppage = event(2L, (long) EventType.GOAL, 45L, 2L);
        EventData halfEnd = event(3L, (long) EventType.GOAL, 45L, null);
        EventData shootout = event(1L, (long) EventType.PENALTY_SHOOTOUT_GOAL, 1L, null);
        EventData noMinute = event(4L, (long) EventType.GOAL, null, null);

        List<EventData> sorted = new ArrayList<>(List.of(shootout, noMinute, stoppage, halfEnd, early));
        sorted.sort(null);

        assertEquals(List.of(early, halfEnd, stoppage, noMinute, shootout), sorted);
    }
}
