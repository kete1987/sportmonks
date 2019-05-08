package com.kete.sportmonks.library.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamScoringPeriods {
    @SerializedName("period")
    @Expose
    private List<TeamPeriod> teamScoringPeriodsList = null;

    public TeamScoringPeriods() {}

    public List<TeamPeriod> getTeamScoringPeriodsList() {
        return teamScoringPeriodsList;
    }
}
