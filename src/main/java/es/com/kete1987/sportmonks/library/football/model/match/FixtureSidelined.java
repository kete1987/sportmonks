package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.player.Sidelined;
import org.jetbrains.annotations.Nullable;

/**
 * Player unavailable for a fixture, as returned by the {@code sidelined} include.
 * The injury/suspension details are only present when {@code sidelined.sideline} is requested,
 * and the player when {@code sidelined.player} is requested.
 * Every field may be absent or null in an API response.
 */
public class FixtureSidelined {
    @Nullable
    private Long id;
    @Nullable
    @SerializedName("fixture_id")
    private Long fixtureId;
    @Nullable
    @SerializedName("sideline_id")
    private Long sidelineId;
    @Nullable
    @SerializedName("participant_id")
    private Long participantId;
    @Nullable
    @SerializedName("player_id")
    private Long playerId;
    @Nullable
    @SerializedName("type_id")
    private Long typeId;
    @Nullable
    private Sidelined sideline;
    @Nullable
    private Player player;

    /** @return the record ID, or null when unavailable. */
    @Nullable
    public Long getId() {
        return id;
    }

    /** @return the fixture ID, or null when unavailable. */
    @Nullable
    public Long getFixtureId() {
        return fixtureId;
    }

    /** @return the ID of the sideline (injury/suspension) record, or null when unavailable. */
    @Nullable
    public Long getSidelineId() {
        return sidelineId;
    }

    /** @return the team/participant ID, or null when unavailable. */
    @Nullable
    public Long getParticipantId() {
        return participantId;
    }

    /** @return the player ID, or null when unavailable. */
    @Nullable
    public Long getPlayerId() {
        return playerId;
    }

    /** @return the reason type ID (injury, suspension, ...), or null when unavailable. */
    @Nullable
    public Long getTypeId() {
        return typeId;
    }

    /** @return the sideline details, or null unless {@code sidelined.sideline} was included. */
    @Nullable
    public Sidelined getSideline() {
        return sideline;
    }

    /** @return the player, or null unless {@code sidelined.player} was included. */
    @Nullable
    public Player getPlayer() {
        return player;
    }
}
