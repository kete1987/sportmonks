package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;

public class Period {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("type_id")
    private Long typeId;
    private Long started;
    private Long ended;
    @SerializedName("counts_from")
    private Long countsFrom;
    private Boolean ticking;
    @SerializedName("sort_order")
    private Long sortOrder;
    private String description;
    @SerializedName("time_added")
    private Long timeAdded;
    @SerializedName("period_length")
    private Long periodLength;
    private Long minutes;
    private Long seconds;

    public Period() {
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

    public Long getStarted() {
        return started;
    }

    public Long getEnded() {
        return ended;
    }

    public Long getCountsFrom() {
        return countsFrom;
    }

    public Boolean getTicking() {
        return ticking;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public Long getTimeAdded() {
        return timeAdded;
    }

    public Long getPeriodLength() {
        return periodLength;
    }

    public Long getMinutes() {
        return minutes;
    }

    public Long getSeconds() {
        return seconds;
    }
}
