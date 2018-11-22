package com.kete.sportmonks.library.model.team_stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvgGoalsPerGameConceded {

    @SerializedName("total")
    @Expose
    private float total;
    @SerializedName("home")
    @Expose
    private float home;
    @SerializedName("away")
    @Expose
    private float away;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getHome() {
        return home;
    }

    public void setHome(float home) {
        this.home = home;
    }

    public float getAway() {
        return away;
    }

    public void setAway(float away) {
        this.away = away;
    }

}
