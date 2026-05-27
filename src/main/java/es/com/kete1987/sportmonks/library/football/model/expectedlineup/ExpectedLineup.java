package es.com.kete1987.sportmonks.library.football.model.expectedlineup;

import es.com.kete1987.sportmonks.library.football.model.player.Player;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;

public class ExpectedLineup {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("team_id")
    private Long teamId;
    private String formation;
    private List<Player> players;

    public ExpectedLineup() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getFormation() {
        return formation;
    }

    public List<Player> getPlayers() {
        return players == null ? null : Collections.unmodifiableList(players);
    }
}
