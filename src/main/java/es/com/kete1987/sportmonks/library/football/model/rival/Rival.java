package es.com.kete1987.sportmonks.library.football.model.rival;

import com.google.gson.annotations.SerializedName;

public class Rival {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("rival_id")
    private Long rivalId;

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
}
