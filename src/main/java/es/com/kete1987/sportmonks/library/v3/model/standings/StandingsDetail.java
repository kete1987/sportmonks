package es.com.kete1987.sportmonks.library.v3.model.standings;

public class StandingsDetail {
    private Long id;
    private String standing_type;
    private Long standing_id;
    private Long type_id;
    private Long value;

    public StandingsDetail() {
    }

    public Long getId() {
        return id;
    }

    public String getStandingType() {
        return standing_type;
    }

    public Long getStandingId() {
        return standing_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public Long getValue() {
        return value;
    }
}
