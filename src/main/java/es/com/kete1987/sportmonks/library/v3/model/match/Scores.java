package es.com.kete1987.sportmonks.library.v3.model.match;

public class Scores {
    private Long id;
    private Long fixture_id;
    private Long type_id;
    private Long participant_id;
    private Score score;
    private String description;

    public Scores() {}

    public Long getId() {
        return id;
    }

    public Long getFixture_id() {
        return fixture_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public Long getParticipant_id() {
        return participant_id;
    }

    public Score getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }
}
