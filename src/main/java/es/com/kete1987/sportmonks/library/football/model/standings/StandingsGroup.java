package es.com.kete1987.sportmonks.library.football.model.standings;

import com.google.gson.annotations.SerializedName;

public class StandingsGroup {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("stage_id")
    private Long stageId;
    private String name;
    @SerializedName("starting_at")
    private String startingAt;
    @SerializedName("ending_at")
    private String endingAt;
    @SerializedName("games_in_current_week")
    private Boolean gamesInCurrentWeek;
    @SerializedName("is_current")
    private Boolean isCurrent;
    @SerializedName("is_finished")
    private Boolean isFinished;
    private Boolean pending;

    public StandingsGroup() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Long getStageId() {
        return stageId;
    }

    public String getName() {
        return name;
    }

    public String getStartingAt() {
        return startingAt;
    }

    public String getEndingAt() {
        return endingAt;
    }

    public Boolean getGamesInCurrentWeek() {
        return gamesInCurrentWeek;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public Boolean getPending() {
        return pending;
    }
}
