package es.com.kete1987.sportmonks.library.football.model.rival;

public class Rival {
    private Long id;
    private Long sport_id;
    private Long team_id;
    private Long rival_id;

    public Rival() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sport_id;
    }

    public Long getTeamId() {
        return team_id;
    }

    public Long getRivalId() {
        return rival_id;
    }
}
