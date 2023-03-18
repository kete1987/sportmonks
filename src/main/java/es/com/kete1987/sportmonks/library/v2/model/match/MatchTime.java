package es.com.kete1987.sportmonks.library.v2.model.match;

public class MatchTime {
    private final String status = null;
    private final int minute = 0;
    private final int extra_minute = 0;
    private final int injury_time = 0;
    private final int second = 0;
    private final int added_time = 0;
    private final MatchDate starting_at = null;

    public MatchTime() {
    }

    public String getStatus() {
        return status;
    }

    public int getMinute() {
        return minute;
    }

    public int getExtraMinute() {
        return extra_minute;
    }

    public int getInjuryTime() {
        return injury_time;
    }

    public int getSecond() {
        return second;
    }

    public int getAddedTime() {
        return added_time;
    }

    public MatchDate getMatchDate() {
        return starting_at;
    }
}
