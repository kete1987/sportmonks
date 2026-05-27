package es.com.kete1987.sportmonks.library.football.model.matchfact;

import com.google.gson.annotations.SerializedName;

public class MatchFact {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("participant_id")
    private Long participantId;
    @SerializedName("participant_type")
    private String participantType;
    private Object value;
    @SerializedName("created_at")
    private String createdAt;

    public MatchFact() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public String getParticipantType() {
        return participantType;
    }

    public Object getValue() {
        return value;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
