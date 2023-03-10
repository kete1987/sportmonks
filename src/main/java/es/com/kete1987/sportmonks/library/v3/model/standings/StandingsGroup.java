package es.com.kete1987.sportmonks.library.v3.model.standings;

public class StandingsGroup {
    private static Long id;
    private static Long sport_id;
    private static Long league_id;
    private static Long season_id;
    private static Long stage_id;
    private static String name;
    private static String starting_at;
    private static String ending_at;
    private static Boolean games_in_current_week;
    private static Boolean is_current;
    private static Boolean is_finished;
    private static Boolean pending;

    public StandingsGroup() {}

    public static Long getId() {
        return id;
    }

    public static Long getSport_id() {
        return sport_id;
    }

    public static Long getLeague_id() {
        return league_id;
    }

    public static Long getSeason_id() {
        return season_id;
    }

    public static Long getStage_id() {
        return stage_id;
    }

    public static String getName() {
        return name;
    }

    public static String getStarting_at() {
        return starting_at;
    }

    public static String getEnding_at() {
        return ending_at;
    }

    public static Boolean getGames_in_current_week() {
        return games_in_current_week;
    }

    public static Boolean getIs_current() {
        return is_current;
    }

    public static Boolean getIs_finished() {
        return is_finished;
    }

    public static Boolean getPending() {
        return pending;
    }
}
