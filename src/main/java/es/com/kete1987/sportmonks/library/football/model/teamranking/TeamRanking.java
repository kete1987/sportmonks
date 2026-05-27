package es.com.kete1987.sportmonks.library.football.model.teamranking;

import com.google.gson.annotations.SerializedName;

public class TeamRanking {
    private Long id;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("season_id")
    private Long seasonId;
    private Long position;
    private Long points;
    @SerializedName("ranking_date")
    private String rankingDate;

    public TeamRanking() {
    }

    public Long getId() {
        return id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Long getPosition() {
        return position;
    }

    public Long getPoints() {
        return points;
    }

    public String getRankingDate() {
        return rankingDate;
    }
}
