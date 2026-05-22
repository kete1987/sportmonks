package es.com.kete1987.sportmonks.library.football.model.matchfact;

public class MatchFact {
    private Long id;
    private Long fixture_id;
    private Long type_id;
    private Long participant_id;
    private String participant_type;
    private Object value;
    private String created_at;

    public MatchFact() {
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

    public String getParticipantType() {
        return participant_type;
    }

    public Object getValue() {
        return value;
    }

    public String getCreatedAt() {
        return created_at;
    }
}
