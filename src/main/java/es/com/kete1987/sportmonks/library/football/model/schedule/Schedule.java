package es.com.kete1987.sportmonks.library.football.model.schedule;

import es.com.kete1987.sportmonks.library.football.model.rounds.Round;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class Schedule {
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("team_id")
    private Long teamId;
    private List<Round> rounds;

    public Schedule() {
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public List<Round> getRounds() {
        return ModelCollections.unmodifiable(rounds);
    }
}
