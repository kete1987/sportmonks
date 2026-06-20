package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RateLimitTrackerTest {

    private final RateLimitTracker tracker = new RateLimitTracker();

    @Test
    void startsEmpty() {
        assertNull(tracker.last());
        assertTrue(tracker.byEntity().isEmpty());
    }

    @Test
    void tracksLastAndKeysByEntity() {
        RateLimit league = rateLimit("league", 2999L);
        RateLimit team = rateLimit("team", 2998L);

        tracker.track(league);
        tracker.track(team);

        assertSame(team, tracker.last());
        assertSame(league, tracker.byEntity().get("league"));
        assertSame(team, tracker.byEntity().get("team"));
    }

    @Test
    void ignoresNullReading() {
        tracker.track(null);

        assertNull(tracker.last());
        assertTrue(tracker.byEntity().isEmpty());
    }

    @Test
    void keepsLastButNotAnEntryWhenEntityIsAbsent() {
        RateLimit noEntity = rateLimit(null, 100L);

        tracker.track(noEntity);

        assertSame(noEntity, tracker.last());
        assertTrue(tracker.byEntity().isEmpty());
    }

    @Test
    void byEntityIsAnImmutableSnapshot() {
        tracker.track(rateLimit("league", 2999L));

        Map<String, RateLimit> snapshot = tracker.byEntity();
        assertThrows(UnsupportedOperationException.class, () -> snapshot.put("team", rateLimit("team", 1L)));
        // A later reading does not leak into the earlier snapshot.
        tracker.track(rateLimit("team", 2998L));
        assertEquals(1, snapshot.size());
    }

    private RateLimit rateLimit(String entity, Long remaining) {
        RateLimit rateLimit = new RateLimit();
        rateLimit.setRequestedEntity(entity);
        rateLimit.setRemaining(remaining);
        rateLimit.setResetsInSeconds(3600L);
        return rateLimit;
    }
}
