package es.com.kete1987.sportmonks.library.v3.model.standings;

public class StandingsForm {
    private Long id;
    private String standing_type;
    private Long standing_id;
    private Long fixture_id;
    private String form;
    private Long sort_order;

    public StandingsForm() {}

    public Long getId() {
        return id;
    }

    public String getStanding_type() {
        return standing_type;
    }

    public Long getStanding_id() {
        return standing_id;
    }

    public Long getFixture_id() {
        return fixture_id;
    }

    public String getForm() {
        return form;
    }

    public Long getSort_order() {
        return sort_order;
    }
}
