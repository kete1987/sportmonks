package com.kete.sportmonks.library.model.team_stats;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamStats {

    @SerializedName("data")
    private List<TeamStat> teamStat = null;


    public List<TeamStat> getTeamStat() {
        return teamStat;
    }
}
