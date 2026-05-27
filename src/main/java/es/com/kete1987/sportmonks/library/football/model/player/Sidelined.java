package es.com.kete1987.sportmonks.library.football.model.player;

import com.google.gson.annotations.SerializedName;

public class Sidelined {
    private Long id;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("type_id")
    private Long typeId;
    private String category;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("games_missed")
    private Long gamesMissed;
    private Boolean completed;

    public Sidelined() {
    }

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public String getCategory() {
        return category;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDdate() {
        return endDate;
    }

    public Long getGamesMissed() {
        return gamesMissed;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
