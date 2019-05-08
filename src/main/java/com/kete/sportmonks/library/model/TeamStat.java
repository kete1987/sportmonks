package com.kete.sportmonks.library.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TeamStat {
    @SerializedName("team_id")
    @Expose
    private Integer teamId;
    @SerializedName("season_id")
    @Expose
    private Integer seasonId;
    @SerializedName("stage_id")
    @Expose
    private Integer stageId;
    @SerializedName("win")
    @Expose
    private TeamStatTotalHomeAway win;
    @SerializedName("draw")
    @Expose
    private TeamStatTotalHomeAway draw;
    @SerializedName("lost")
    @Expose
    private TeamStatTotalHomeAway lost;
    @SerializedName("goals_for")
    @Expose
    private TeamStatTotalHomeAway goalsFor;
    @SerializedName("goals_against")
    @Expose
    private TeamStatTotalHomeAway goalsAgainst;
    @SerializedName("clean_sheet")
    @Expose
    private TeamStatTotalHomeAway cleanSheet;
    @SerializedName("avg_goals_per_game_scored")
    @Expose
    private TeamStatTotalHomeAway avgGoalsPerGameScored;
    @SerializedName("avg_goals_per_game_conceded")
    @Expose
    private TeamStatTotalHomeAway avgGoalsPerGameConceded;
    @SerializedName("avg_first_goal_scored")
    @Expose
    private TeamStatTotalHomeAway avgFirstGoalScored;
    @SerializedName("avg_first_goal_conceded")
    @Expose
    private TeamStatTotalHomeAway avgFirstGoalConceded;
    @SerializedName("failed_to_score")
    @Expose
    private TeamStatTotalHomeAway failedToScore;
    @SerializedName("scoring_minutes")
    @Expose
    private List<TeamScoringPeriods> scoringPeriodsList;

    public TeamStat() {}

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public TeamStatTotalHomeAway getWin() {
        return win;
    }

    public void setWin(TeamStatTotalHomeAway win) {
        this.win = win;
    }

    public TeamStatTotalHomeAway getDraw() {
        return draw;
    }

    public void setDraw(TeamStatTotalHomeAway draw) {
        this.draw = draw;
    }

    public TeamStatTotalHomeAway getLost() {
        return lost;
    }

    public void setLost(TeamStatTotalHomeAway lost) {
        this.lost = lost;
    }

    public TeamStatTotalHomeAway getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(TeamStatTotalHomeAway goalsFor) {
        this.goalsFor = goalsFor;
    }

    public TeamStatTotalHomeAway getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(TeamStatTotalHomeAway goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public TeamStatTotalHomeAway getCleanSheet() {
        return cleanSheet;
    }

    public void setCleanSheet(TeamStatTotalHomeAway cleanSheet) {
        this.cleanSheet = cleanSheet;
    }

    public TeamStatTotalHomeAway getAvgGoalsPerGameScored() {
        return avgGoalsPerGameScored;
    }

    public void setAvgGoalsPerGameScored(TeamStatTotalHomeAway avgGoalsPerGameScored) {
        this.avgGoalsPerGameScored = avgGoalsPerGameScored;
    }

    public TeamStatTotalHomeAway getAvgGoalsPerGameConceded() {
        return avgGoalsPerGameConceded;
    }

    public void setAvgGoalsPerGameConceded(TeamStatTotalHomeAway avgGoalsPerGameConceded) {
        this.avgGoalsPerGameConceded = avgGoalsPerGameConceded;
    }

    public TeamStatTotalHomeAway getAvgFirstGoalScored() {
        return avgFirstGoalScored;
    }

    public void setAvgFirstGoalScored(TeamStatTotalHomeAway avgFirstGoalScored) {
        this.avgFirstGoalScored = avgFirstGoalScored;
    }

    public TeamStatTotalHomeAway getAvgFirstGoalConceded() {
        return avgFirstGoalConceded;
    }

    public void setAvgFirstGoalConceded(TeamStatTotalHomeAway avgFirstGoalConceded) {
        this.avgFirstGoalConceded = avgFirstGoalConceded;
    }

    public TeamStatTotalHomeAway getFailedToScore() {
        return failedToScore;
    }

    public void setFailedToScore(TeamStatTotalHomeAway failedToScore) {
        this.failedToScore = failedToScore;
    }

    public List<TeamScoringPeriods> getScoringPeriodsList() {
        return scoringPeriodsList;
    }

    public List<TeamPeriod> getTeamScoringPeriods() {
        return scoringPeriodsList != null && !scoringPeriodsList.isEmpty() ? scoringPeriodsList.get(0).getTeamScoringPeriodsList() : new ArrayList<>();
    }

    public void setScoringPeriodsList(List<TeamScoringPeriods> scoringPeriodsList) {
        this.scoringPeriodsList = scoringPeriodsList;
    }
}
