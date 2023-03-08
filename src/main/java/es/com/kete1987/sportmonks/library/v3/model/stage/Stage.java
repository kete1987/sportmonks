package es.com.kete1987.sportmonks.library.v3.model.stage;

public class Stage {
    private Long id;
    private Long sport_id;
    private Long league_id;
    private Long season_id;
    private Long type_id;
    private String name;
    private Long sort_order;
    private Boolean finished;
    private Boolean is_current;
    private String starting_at;
    private String ending_at;
    private String games_in_current_week;

    public Stage() {}

    public Long getId() {
        return id;
    }

    public Long getSport_id() {
        return sport_id;
    }

    public Long getLeague_id() {
        return league_id;
    }

    public Long getSeason_id() {
        return season_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public String getName() {
        return name;
    }

    public Long getSort_order() {
        return sort_order;
    }

    public Boolean getFinished() {
        return finished;
    }

    public Boolean getIs_current() {
        return is_current;
    }

    public String getStarting_at() {
        return starting_at;
    }

    public String getEnding_at() {
        return ending_at;
    }

    public String getGames_in_current_week() {
        return games_in_current_week;
    }
}
