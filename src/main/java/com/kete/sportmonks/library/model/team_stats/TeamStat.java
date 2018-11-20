package com.kete.sportmonks.library.model.team_stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private Win win;
    @SerializedName("draw")
    @Expose
    private Draw draw;
    @SerializedName("lost")
    @Expose
    private Lost lost;
    @SerializedName("goals_for")
    @Expose
    private GoalsFor goalsFor;
    @SerializedName("goals_against")
    @Expose
    private GoalsAgainst goalsAgainst;
    @SerializedName("clean_sheet")
    @Expose
    private CleanSheet cleanSheet;
    @SerializedName("avg_goals_per_game_scored")
    @Expose
    private AvgGoalsPerGameScored avgGoalsPerGameScored;
    @SerializedName("avg_goals_per_game_conceded")
    @Expose
    private AvgGoalsPerGameConceded avgGoalsPerGameConceded;
    @SerializedName("avg_first_goal_scored")
    @Expose
    private AvgFirstGoalScored avgFirstGoalScored;
    @SerializedName("avg_first_goal_conceded")
    @Expose
    private AvgFirstGoalConceded avgFirstGoalConceded;
    @SerializedName("failed_to_score")
    @Expose
    private FailedToScore failedToScore;

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

    public Win getWin() {
        return win;
    }

    public void setWin(Win win) {
        this.win = win;
    }

    public Draw getDraw() {
        return draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    public Lost getLost() {
        return lost;
    }

    public void setLost(Lost lost) {
        this.lost = lost;
    }

    public GoalsFor getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(GoalsFor goalsFor) {
        this.goalsFor = goalsFor;
    }

    public GoalsAgainst getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(GoalsAgainst goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public CleanSheet getCleanSheet() {
        return cleanSheet;
    }

    public void setCleanSheet(CleanSheet cleanSheet) {
        this.cleanSheet = cleanSheet;
    }


    public AvgGoalsPerGameScored getAvgGoalsPerGameScored() {
        return avgGoalsPerGameScored;
    }

    public void setAvgGoalsPerGameScored(AvgGoalsPerGameScored avgGoalsPerGameScored) {
        this.avgGoalsPerGameScored = avgGoalsPerGameScored;
    }

    public AvgGoalsPerGameConceded getAvgGoalsPerGameConceded() {
        return avgGoalsPerGameConceded;
    }

    public void setAvgGoalsPerGameConceded(AvgGoalsPerGameConceded avgGoalsPerGameConceded) {
        this.avgGoalsPerGameConceded = avgGoalsPerGameConceded;
    }

    public AvgFirstGoalScored getAvgFirstGoalScored() {
        return avgFirstGoalScored;
    }

    public void setAvgFirstGoalScored(AvgFirstGoalScored avgFirstGoalScored) {
        this.avgFirstGoalScored = avgFirstGoalScored;
    }

    public AvgFirstGoalConceded getAvgFirstGoalConceded() {
        return avgFirstGoalConceded;
    }

    public void setAvgFirstGoalConceded(AvgFirstGoalConceded avgFirstGoalConceded) {
        this.avgFirstGoalConceded = avgFirstGoalConceded;
    }

    public FailedToScore getFailedToScore() {
        return failedToScore;
    }

    public void setFailedToScore(FailedToScore failedToScore) {
        this.failedToScore = failedToScore;
    }

}
