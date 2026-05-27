package es.com.kete1987.sportmonks.library.football.model.expectedgoal;

import com.google.gson.annotations.SerializedName;

public class ExpectedGoal {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("participant_id")
    private Long participantId;
    @SerializedName("participant_type")
    private String participantType;
    private Double possession;
    @SerializedName("goals_expected")
    private Double goalsExpected;
    @SerializedName("scoring_chances")
    private Double scoringChances;
    @SerializedName("player_id")
    private Long playerId;
    private Integer minute;
    @SerializedName("period_id")
    private Long periodId;

    public ExpectedGoal() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public String getParticipantType() {
        return participantType;
    }

    public Double getPossession() {
        return possession;
    }

    public Double getGoalsExpected() {
        return goalsExpected;
    }

    public Double getScoringChances() {
        return scoringChances;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Integer getMinute() {
        return minute;
    }

    public Long getPeriodId() {
        return periodId;
    }
}
