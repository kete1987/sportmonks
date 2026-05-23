package es.com.kete1987.sportmonks.library.football.model.teamranking;

public class TeamRanking {
    private Long id;
    private Long team_id;
    private Long league_id;
    private Long season_id;
    private Long position;
    private Long points;
    private String ranking_date;

    public TeamRanking() {
    }

    public Long getId() {
        return id;
    }

    public Long getTeamId() {
        return team_id;
    }

    public Long getLeagueId() {
        return league_id;
    }

    public Long getSeasonId() {
        return season_id;
    }

    public Long getPosition() {
        return position;
    }

    public Long getPoints() {
        return points;
    }

    public String getRankingDate() {
        return ranking_date;
    }
}
