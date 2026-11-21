package es.com.kete1987.sportmonks.library.football.model.rival;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import org.jetbrains.annotations.Nullable;

public class Rival {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("rival_id")
    private Long rivalId;
    @SerializedName("rival")
    private Team rivalTeam;
    private Team team;

    public Rival() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getRivalId() {
        return rivalId;
    }

    /** @return the rival team, or null if the {@code rival} include is absent. */
    @Nullable
    public Team getRival() {
        return rivalTeam;
    }

    /** @return the team the rivalry belongs to, or null if the {@code team} include is absent. */
    @Nullable
    public Team getTeam() {
        return team;
    }
}
