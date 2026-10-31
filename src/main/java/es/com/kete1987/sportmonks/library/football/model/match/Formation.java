package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

/** Official fixture formation. Every field may be absent or null in an API response. */
public class Formation {
    @Nullable
    private Long id;
    @Nullable
    @SerializedName("fixture_id")
    private Long fixtureId;
    @Nullable
    @SerializedName("participant_id")
    private Long participantId;
    @Nullable
    @SerializedName("formation")
    private String system;
    @Nullable
    private String location;

    /** @return the formation record ID, or null when unavailable. */
    @Nullable
    public Long getId() {
        return id;
    }

    /** @return the fixture ID, or null when unavailable. */
    @Nullable
    public Long getFixtureId() {
        return fixtureId;
    }

    /** @return the team/participant ID, or null when unavailable. */
    @Nullable
    public Long getParticipantId() {
        return participantId;
    }

    /** @return the raw system (e.g. {@code "4-2-3-1"}), or null when unavailable. */
    @Nullable
    public String getFormation() {
        return system;
    }

    /** @return the location (usually {@code "home"} or {@code "away"}), or null when unavailable. */
    @Nullable
    public String getLocation() {
        return location;
    }
}
