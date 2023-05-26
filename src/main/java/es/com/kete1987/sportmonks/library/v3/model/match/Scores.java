package es.com.kete1987.sportmonks.library.v3.model.match;

public class Scores {
    private Long id;
    private Long fixture_id;
    private Long type_id;
    private Long participant_id;
    private Score score;
    private String description;

    public Scores() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public Long getParticipantId() {
        return participant_id;
    }

    public Score getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }
}
