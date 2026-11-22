package es.com.kete1987.sportmonks.library.odds.util;

/**
 * Ids of the most used betting markets, as {@code GET /v3/odds/markets} reports them. Pass them to
 * the odds methods ({@code getMatchOddsByMarket(id, String.valueOf(MarketIds.CORRECT_SCORE))}) or to
 * the {@code markets:} filter ({@code "markets:" + MarketIds.FULLTIME_RESULT}).
 *
 * <p>Each name is the {@code developer_name} of the market, except {@link #FIRST_HALF_GOALS}, whose
 * {@code developer_name} ({@code 1ST_HALF_GOALS}) is not a valid Java identifier. The catalogue has
 * more than two hundred markets; use {@code getAllMarkets()} or {@code searchMarkets(...)} for the rest.
 */
public final class MarketIds {
    private MarketIds() {
        // Constants only: not meant to be instantiated.
    }

    public static final long FULLTIME_RESULT = 1L;
    public static final long DOUBLE_CHANCE = 2L;
    public static final long MATCH_GOALS = 4L;
    public static final long ASIAN_HANDICAP = 6L;
    public static final long GOAL_LINE = 7L;
    public static final long DRAW_NO_BET = 10L;
    public static final long GOALS_ODD_EVEN = 12L;
    public static final long RESULT_BOTH_TEAMS_TO_SCORE = 13L;
    public static final long BOTH_TEAMS_TO_SCORE = 14L;
    public static final long FIRST_HALF_GOALS = 28L;
    public static final long HALF_TIME_FULL_TIME = 29L;
    public static final long HALF_TIME_CORRECT_SCORE = 30L;
    public static final long HALF_TIME_RESULT = 31L;
    public static final long RESULT_TOTAL_GOALS = 37L;
    public static final long ODD_EVEN = 44L;
    public static final long WIN_TO_NIL = 46L;
    public static final long HOME_AWAY = 52L;
    public static final long CORRECT_SCORE = 57L;
    public static final long CORRECT_SCORE_1ST_HALF = 58L;
    public static final long TOTAL_CORNERS = 68L;
    public static final long GOALS_OVER_UNDER = 80L;
    public static final long ALTERNATIVE_TOTAL_GOALS = 81L;
    public static final long TOTAL_GOALS_BOTH_TEAMS_TO_SCORE = 82L;
    public static final long TEAM_TOTAL_GOALS = 86L;
    public static final long GOALSCORERS = 90L;
    public static final long EXACT_TOTAL_GOALS = 93L;
}
