package es.com.kete1987.sportmonks.library.football.model.totw;

import es.com.kete1987.sportmonks.library.football.model.player.Player;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class TeamOfTheWeek {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("round_id")
    private Long roundId;
    @SerializedName("league_id")
    private Long leagueId;
    private List<Player> players;

    public TeamOfTheWeek() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getRoundId() {
        return roundId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public List<Player> getPlayers() {
        return ModelCollections.unmodifiable(players);
    }
}
