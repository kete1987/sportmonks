package com.kete.sportmonks.library.model.team_stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Draw {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("home")
    @Expose
    private Integer home;
    @SerializedName("away")
    @Expose
    private Integer away;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getAway() {
        return away;
    }

    public void setAway(Integer away) {
        this.away = away;
    }

}
