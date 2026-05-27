package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;

public class Scores {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("participant_id")
    private Long participantId;
    private Score score;
    private String description;

    public Scores() {
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

    public Score getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }
}
