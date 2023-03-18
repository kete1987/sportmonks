package es.com.kete1987.sportmonks.library.v2.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamScoringPeriods {
    @SerializedName("period")
    @Expose
    private final List<TeamPeriod> teamScoringPeriodsList = null;

    public TeamScoringPeriods() {
    }

    public List<TeamPeriod> getTeamScoringPeriodsList() {
        return teamScoringPeriodsList;
    }
}
