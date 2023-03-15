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

    public String getModelType() {
        return model_type;
    }

    public Long getModelId() {
        return model_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public Long getPosition() {
        return position;
    }
}
