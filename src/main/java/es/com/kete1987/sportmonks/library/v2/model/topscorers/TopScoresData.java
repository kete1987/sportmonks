package es.com.kete1987.sportmonks.library.v2.model.topscorers;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopScoresData {
    @SerializedName(value = "scorers", alternate = {"goalscorers", "aggregatedGoalscorers"})
    private TopScoresPlayerData scorers;
    private final int id = -1;
    private final String name = null;
    private final int id_league = -1;
    private final boolean is_current_season = false;

    public TopScoresData() {
    }

    public List<TopScoresPlayer> getListOfTopScores() {
        return scorers.getListOfTopScores();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIdLeague() {
        return id_league;
    }

    public boolean isCurrentSeason() {
        return is_current_season;
    }
}
