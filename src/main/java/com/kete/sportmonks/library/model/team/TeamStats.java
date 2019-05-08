package com.kete.sportmonks.library.model.team;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamStats {
    @SerializedName("data")
    private List<TeamStat> teamStatList = null;

    public TeamStats() {}

    public List<TeamStat> getTeamStatList() {
        return teamStatList;
    }
}
