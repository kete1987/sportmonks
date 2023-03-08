package es.com.kete1987.sportmonks.library.v3.model.standings;

public class StandingsRule {
    private Long id;
    private String model_type;
    private Long model_id;
    private Long type_id;
    private Long position;

    public StandingsRule() {}

    public Long getId() {
        return id;
    }

    public String getModel_type() {
        return model_type;
    }

    public Long getModel_id() {
        return model_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public Long getPosition() {
        return position;
    }
}
