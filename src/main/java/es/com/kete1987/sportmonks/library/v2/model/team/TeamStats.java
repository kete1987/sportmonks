package es.com.kete1987.sportmonks.library.v2.model.team;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamStats {
    @SerializedName("data")
    private final List<TeamStat> teamStatList = null;

    public TeamStats() {
    }

    public List<TeamStat> getTeamStatList() {
        return teamStatList;
    }
}
