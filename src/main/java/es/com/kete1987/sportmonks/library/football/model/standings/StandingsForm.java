package es.com.kete1987.sportmonks.library.football.model.standings;

import com.google.gson.annotations.SerializedName;

public class StandingsForm {
    private Long id;
    @SerializedName("standing_type")
    private String standingType;
    @SerializedName("standing_id")
    private Long standingId;
    @SerializedName("fixture_id")
    private Long fixtureId;
    private String form;
    @SerializedName("sort_order")
    private Long sortOrder;

    public StandingsForm() {
    }

    public Long getId() {
        return id;
    }

    public String getStandingType() {
        return standingType;
    }

    public Long getStandingId() {
        return standingId;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public String getForm() {
        return form;
    }

    public Long getSortOrder() {
        return sortOrder;
    }
}
