package es.com.kete1987.sportmonks.library.football.model.expectedgoal;

public class ExpectedGoal {
    private Long id;
    private Long fixture_id;
    private Long participant_id;
    private String participant_type;
    private Double possession;
    private Double goals_expected;
    private Double scoring_chances;
    private Long player_id;
    private Integer minute;
    private Long period_id;

    public ExpectedGoal() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getParticipantId() {
        return participant_id;
    }

    public String getParticipantType() {
        return participant_type;
    }

    public Double getPossession() {
        return possession;
    }

    public Double getGoalsExpected() {
        return goals_expected;
    }

    public Double getScoringChances() {
        return scoring_chances;
    }

    public Long getPlayerId() {
        return player_id;
    }

    public Integer getMinute() {
        return minute;
    }

    public Long getPeriodId() {
        return period_id;
    }
}
