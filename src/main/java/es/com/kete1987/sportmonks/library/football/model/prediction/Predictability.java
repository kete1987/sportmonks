package es.com.kete1987.sportmonks.library.football.model.prediction;

public class Predictability {
    private Long league_id;
    private Double predictability;
    private Long season_id;

    public Predictability() {
    }

    public Long getLeagueId() {
        return league_id;
    }

    public Double getPredictability() {
        return predictability;
    }

    public Long getSeasonId() {
        return season_id;
    }
}
