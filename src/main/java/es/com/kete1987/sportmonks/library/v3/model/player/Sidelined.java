package es.com.kete1987.sportmonks.library.v3.model.player;

public class Sidelined {
    private Long id;
    private Long player_id;
    private Long type_id;
    private String category;
    private Long team_id;
    private Long season_id;
    private String start_date;
    private String end_date;
    private Long games_missed;
    private Boolean completed;

    public Sidelined() {}

    public Long getId() {
        return id;
    }

    public Long getPlayer_id() {
        return player_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public String getCategory() {
        return category;
    }

    public Long getTeam_id() {
        return team_id;
    }

    public Long getSeason_id() {
        return season_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public Long getGames_missed() {
        return games_missed;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
