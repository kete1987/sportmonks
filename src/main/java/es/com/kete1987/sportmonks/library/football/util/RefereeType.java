package es.com.kete1987.sportmonks.library.football.util;

/**
 * Type ids of the match officials returned by the fixture {@code referees} include
 * (see {@link es.com.kete1987.sportmonks.library.football.model.match.FixtureReferee#getTypeId()}).
 * Values checked against {@code /v3/core/types}.
 */
public final class RefereeType {
    public static final int REFEREE = 6; // REFEREE: main referee.
    public static final int FIRST_ASSISTANT = 7; // REFEREE_TWO: 1st assistant referee.
    public static final int SECOND_ASSISTANT = 8; // REFEREE_THREE: 2nd assistant referee.
    public static final int FOURTH_OFFICIAL = 9; // REFEREE_FOUR: 4th official.

    private RefereeType() {
        // constants holder
    }
}
