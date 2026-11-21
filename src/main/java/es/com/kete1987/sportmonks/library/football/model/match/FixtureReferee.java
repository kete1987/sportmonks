package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.football.model.referee.Referee;
import es.com.kete1987.sportmonks.library.football.util.RefereeType;
import org.jetbrains.annotations.Nullable;

/**
 * Match official assigned to a fixture, as returned by the {@code referees} include.
 * The official's details are only present when {@code referees.referee} is requested.
 * Every field may be absent or null in an API response.
 */
public class FixtureReferee {
    @Nullable
    private Long id;
    @Nullable
    @SerializedName("fixture_id")
    private Long fixtureId;
    @Nullable
    @SerializedName("referee_id")
    private Long refereeId;
    @Nullable
    @SerializedName("type_id")
    private Long typeId;
    @Nullable
    private Referee referee;

    /** @return the assignment record ID, or null when unavailable. */
    @Nullable
    public Long getId() {
        return id;
    }

    /** @return the fixture ID, or null when unavailable. */
    @Nullable
    public Long getFixtureId() {
        return fixtureId;
    }

    /** @return the referee ID, or null when unavailable. */
    @Nullable
    public Long getRefereeId() {
        return refereeId;
    }

    /** @return the role of the official (see {@link RefereeType}), or null when unavailable. */
    @Nullable
    public Long getTypeId() {
        return typeId;
    }

    /** @return the official's details, or null unless {@code referees.referee} was included. */
    @Nullable
    public Referee getReferee() {
        return referee;
    }

    /** @return {@code true} if this official is the main referee ({@link RefereeType#REFEREE}). */
    public boolean isMainReferee() {
        return typeId != null && typeId.intValue() == RefereeType.REFEREE;
    }
}
