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

    public static Long getSportId() {
        return sport_id;
    }

    public static Long getLeagueId() {
        return league_id;
    }

    public static Long getSeasonId() {
        return season_id;
    }

    public static Long getStageId() {
        return stage_id;
    }

    public static String getName() {
        return name;
    }

    public static String getStartingAt() {
        return starting_at;
    }

    public static String getEndingAt() {
        return ending_at;
    }

    public static Boolean getGamesInCurrentWeek() {
        return games_in_current_week;
    }

    public static Boolean getIsCurrent() {
        return is_current;
    }

    public static Boolean getIsFinished() {
        return is_finished;
    }

    public static Boolean getPending() {
        return pending;
    }
}
