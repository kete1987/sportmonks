package es.com.kete1987.sportmonks.library.v3.util;

public class MatchStatus {
    public static final int NOT_STARTED = 1;
    public static final int INPLAY_1st_HALF = 2;
    public static final int HT = 3; // Half-time
    public static final int BREAK = 4; // Regular time finished
    public static final int FT = 5; // Full time
    public static final int INPLAY_ET = 6; // Extra time
    public static final int AET = 7; // Finished after extra-time
    public static final int FT_PEN = 8; // Full time after penalties
    public static final int INPLAY_PENALTIES = 9; // Penalties
    public static final int POSTPONED = 10;
    public static final int SUSPENDED = 11;
    public static final int CANCELLED = 12;
    public static final int TBA = 13; // To be announced
    public static final int WO = 14; // Walk over
    public static final int ABANDONED = 15;
    public static final int DELAYED = 16;
    public static final int AWARDED = 17;
    public static final int INTERRUPTED = 18;
    public static final int AWAITING_UPDATES = 19;
    public static final int DELETED = 20;
    public static final int EXTRA_TIME_BREAK = 21;
    public static final int INPLAY_2ND_HALF = 22;
    public static final int INPLAY_ET_2ND_HALF = 23;
    public static final int PEN_BREAK = 25;
    public static final int PENDING = 26;

    public static boolean isLiveMatch(int status) {
        return status == INPLAY_1st_HALF || status == HT || status == BREAK || status == INPLAY_ET || status == INPLAY_PENALTIES ||
                status == EXTRA_TIME_BREAK || status == INPLAY_2ND_HALF || status == INPLAY_ET_2ND_HALF || status == PEN_BREAK;
    }

    public static boolean isMatchFinished(int status) {
        return status == FT || status == AET || status == FT_PEN;
    }

    public static String getStatus(int status) {
        switch (status) {
            case NOT_STARTED:
                return "Sin comenzar";
            case INPLAY_1st_HALF:
                return "En juego (1ª parte)";
            case HT:
                return "Descanso";
            case BREAK:
                return "Descanso (90 min)";
            case FT:
            case AET:
            case FT_PEN:
                return "Finalizado";
            case INPLAY_ET:
                return "En juego (1ª parte prórroga)";
            case INPLAY_PENALTIES:
                return "Penaltis";
            case POSTPONED:
                return "Aplazado";
            case SUSPENDED:
                return "Suspendido";
            case CANCELLED:
                return "Cancelado";
            case TBA:
                return "Horario sin confirmar";
            case WO:
                return "No presentado";
            case ABANDONED:
                return "Abandonado";
            case DELAYED:
                return "Retrasado";
            case AWARDED:
                return "Ganador se decidirá de forma externa";
            case INTERRUPTED:
                return "Interrumpido";
            case AWAITING_UPDATES:
                return "Esperando actualizaciones";
            case DELETED:
                return "Borrado";
            case EXTRA_TIME_BREAK:
                return "Descanso (prórroga)";
            case INPLAY_2ND_HALF:
                return "En juego (2ª parte)";
            case INPLAY_ET_2ND_HALF:
                return "En juego (2ª parte prórroga)";
            case PEN_BREAK:
                return "Descanso (penaltis)";
            case PENDING:
                return "Pendiente";
            default:
                return "N/A";
        }
    }
}
