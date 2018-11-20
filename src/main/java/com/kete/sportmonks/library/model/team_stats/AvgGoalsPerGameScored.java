package com.kete.sportmonks.library.model.team_stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvgGoalsPerGameScored {

    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("home")
    @Expose
    private Double home;
    @SerializedName("away")
    @Expose
    private Double away;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getHome() {
        return home;
    }

    public void setHome(Double home) {
        this.home = home;
    }

    public Double getAway() {
        return away;
    }

    public void setAway(Double away) {
        this.away = away;
    }

}
