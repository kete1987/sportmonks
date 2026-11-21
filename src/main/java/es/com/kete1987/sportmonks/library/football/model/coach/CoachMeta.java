package es.com.kete1987.sportmonks.library.football.model.coach;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

/**
 * Pivot data attached to a {@link Coach} returned by the fixture {@code coaches} include:
 * it tells which team the coach was in charge of in that fixture.
 * Every field may be absent or null in an API response.
 */
public class CoachMeta {
    @Nullable
    @SerializedName("fixture_id")
    private Long fixtureId;
    @Nullable
    @SerializedName("coach_id")
    private Long coachId;
    @Nullable
    @SerializedName("participant_id")
    private Long participantId;

    /** @return the fixture ID, or null when unavailable. */
    @Nullable
    public Long getFixtureId() {
        return fixtureId;
    }

    /** @return the coach ID, or null when unavailable. */
    @Nullable
    public Long getCoachId() {
        return coachId;
    }

    /** @return the team/participant ID the coach led in the fixture, or null when unavailable. */
    @Nullable
    public Long getParticipantId() {
        return participantId;
    }
}
