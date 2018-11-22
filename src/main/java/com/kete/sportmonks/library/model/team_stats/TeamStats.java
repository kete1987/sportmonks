package com.kete.sportmonks.library.model.team_stats;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamStats {

    @SerializedName("data")
    private TeamStat teamStat = null;


    public TeamStat getTeamStat() {
        return teamStat;
    }
}
