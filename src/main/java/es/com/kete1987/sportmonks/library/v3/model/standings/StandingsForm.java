package es.com.kete1987.sportmonks.library.v3.model.standings;

public class StandingsForm {
    private Long id;
    private String standing_type;
    private Long standing_id;
    private Long fixture_id;
    private String form;
    private Long sort_order;

    public StandingsForm() {
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

    public Long getFixtureId() {
        return fixture_id;
    }

    public String getForm() {
        return form;
    }

    public Long getSortOrder() {
        return sort_order;
    }
}
