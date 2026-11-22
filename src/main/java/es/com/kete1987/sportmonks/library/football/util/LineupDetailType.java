package es.com.kete1987.sportmonks.library.football.util;

/**
 * Ids of the detail types that appear in {@code lineups.details} and that the
 * {@code lineupdetailTypes} filter accepts, for example
 * {@code getMatchDetail(id, new String[]{"lineups.details"}, "lineupdetailTypes:" + LineupDetailType.CAPTAIN)}.
 *
 * <p>They are ids of {@code GET /core/types}, named after the {@code developer_name} of the type.
 * They share their ids with {@link StatisticsType} for the types both have, but a filter on lineup
 * details should read from this class. The catalogue has more types than these: only those a
 * real lineup carries are listed.
 */
public final class LineupDetailType {
    private LineupDetailType() {
        // Constants only: not meant to be instantiated.
    }

    public static final int CAPTAIN = 40;
    public static final int SHOTS_OFF_TARGET = 41;
    public static final int SHOTS_TOTAL = 42;
    public static final int OFFSIDES = 51;
    public static final int GOALS = 52;
    public static final int FOULS = 56;
    public static final int SAVES = 57;
    public static final int SHOTS_BLOCKED = 58;
    public static final int HIT_WOODWORK = 64;
    public static final int TACKLES = 78;
    public static final int ASSISTS = 79;
    public static final int PASSES = 80;
    public static final int REDCARDS = 83;
    public static final int YELLOWCARDS = 84;
    public static final int YELLOWRED_CARDS = 85;
    public static final int SHOTS_ON_TARGET = 86;
    public static final int GOALS_CONCEDED = 88;
    public static final int DISPOSSESSED = 94;
    public static final int FOULS_DRAWN = 96;
    public static final int BLOCKED_SHOTS = 97;
    public static final int TOTAL_CROSSES = 98;
    public static final int ACCURATE_CROSSES = 99;
    public static final int INTERCEPTIONS = 100;
    public static final int CLEARANCES = 101;
    public static final int SAVES_INSIDE_BOX = 104;
    public static final int TOTAL_DUELS = 105;
    public static final int DUELS_WON = 106;
    public static final int AERIALS_WON = 107;
    public static final int DRIBBLED_ATTEMPTS = 108;
    public static final int SUCCESSFUL_DRIBBLES = 109;
    public static final int DRIBBLED_PAST = 110;
    public static final int ACCURATE_PASSES = 116;
    public static final int KEY_PASSES = 117;
    public static final int RATING = 118;
    public static final int MINUTES_PLAYED = 119;
    public static final int TOUCHES = 120;
    public static final int LONG_BALLS = 122;
    public static final int LONG_BALLS_WON = 123;
    public static final int THROUGH_BALLS = 124;
    public static final int THROUGH_BALLS_WON = 125;
    public static final int BIG_CHANCES_CREATED = 580;
    public static final int BIG_CHANCES_MISSED = 581;
    public static final int DUELS_LOST = 1491;
    public static final int GOALKEEPER_GOALS_CONCEDED = 1535;
    public static final int ACCURATE_PASSES_PERCENTAGE = 1584;
}
