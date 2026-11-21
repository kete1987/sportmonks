package es.com.kete1987.sportmonks.library.football.model.statistic;

/** Entity whose season statistics are requested: the {@code {entity}} of {@code statistics/seasons/{entity}/{id}}. */
public enum SeasonStatisticEntity {
    PLAYERS("players"),
    TEAMS("teams"),
    COACHES("coaches"),
    REFEREES("referees");

    private final String path;

    SeasonStatisticEntity(String path) {
        this.path = path;
    }

    /** Path segment the API expects for this entity. */
    public String getPath() {
        return path;
    }
}
