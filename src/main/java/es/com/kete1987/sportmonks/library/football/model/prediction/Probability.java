package es.com.kete1987.sportmonks.library.football.model.prediction;

public class Probability {
    private Long id;
    private Long fixture_id;
    private Long type_id;
    private Double home;
    private Double draw;
    private Double away;
    private Double yes;
    private Double no;

    public Probability() {
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

    public Double getHome() {
        return home;
    }

    public Double getDraw() {
        return draw;
    }

    public Double getAway() {
        return away;
    }

    public Double getYes() {
        return yes;
    }

    public Double getNo() {
        return no;
    }
}
