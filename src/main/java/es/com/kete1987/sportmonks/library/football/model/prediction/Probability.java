package es.com.kete1987.sportmonks.library.football.model.prediction;

import com.google.gson.annotations.SerializedName;

public class Probability {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("type_id")
    private Long typeId;
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
        return fixtureId;
    }

    public Long getTypeId() {
        return typeId;
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
