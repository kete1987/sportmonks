package es.com.kete1987.sportmonks.library.v3.model.rounds;

public class Round {
    private Long id;
    private Long sport_id;
    private Long league_id;
    private Long season_id;
    private Long stage_id;
    private String name;
    private Boolean finished;
    private Boolean is_current;
    private String starting_at;
    private String ending_at;
    private Boolean games_in_current_week;

    public Round() {}

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

    public Long getStage_id() {
        return stage_id;
    }

    public String getName() {
        return name;
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

    public Boolean getGames_in_current_week() {
        return games_in_current_week;
    }
}
