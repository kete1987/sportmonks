package es.com.kete1987.sportmonks.library.football.model.prediction;

import com.google.gson.annotations.SerializedName;

public class Predictability {
    @SerializedName("league_id")
    private Long leagueId;
    private Double predictability;
    @SerializedName("season_id")
    private Long seasonId;

    public Predictability() {
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Double getPredictability() {
        return predictability;
    }

    public Long getSeasonId() {
        return seasonId;
    }
}
