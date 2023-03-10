package es.com.kete1987.sportmonks.library.v3.util;

public class MatchStatus {
    public static int NOT_STARTED = 1;
    public static int INPLAY_1st_HALF = 2;
    public static int HT = 3; // Half-time
    public static int BREAK = 4; // Regular time finished
    public static int FT = 5; // Full time
    public static int INPLAY_ET = 6; // Extra time
    public static int AET = 7; // Finished after extra-time
    public static int FT_PEN = 8; // Full time after penalties
    public static int INPLAY_PENALTIES = 9; // Penalties
    public static int POSTPONED = 10;
    public static int SUSPENDED = 11;
    public static int CANCELLED = 12;
    public static int TBA = 13; // To be announced
    public static int WO = 14; // Walk over
    public static int ABANDONED = 15;
    public static int DELAYED = 16;
    public static int AWARDED = 17;
    public static int INTERRUPTED = 18;
    public static int AWAITING_UPDATES = 19;
    public static int DELETED = 20;
    public static int EXTRA_TIME_BREAK = 21;
    public static int INPLAY_2ND_HALF = 22;
    public static int INPLAY_ET_2ND_HALF = 23;
    public static int PEN_BREAK = 25;
    public static int PENDING = 26;

    public static boolean isLiveMatch(int status) {
        return status == INPLAY_1st_HALF || status == HT || status == BREAK || status == INPLAY_ET || status == INPLAY_PENALTIES ||
                status == EXTRA_TIME_BREAK || status == INPLAY_2ND_HALF || status == INPLAY_ET_2ND_HALF || status == PEN_BREAK;
    }

    public static boolean isMatchFinished(int status) {
        return status == FT || status == AET || status == FT_PEN;
    }
}
