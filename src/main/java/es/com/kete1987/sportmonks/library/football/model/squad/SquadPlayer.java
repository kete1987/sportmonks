package es.com.kete1987.sportmonks.library.football.model.squad;

import com.google.gson.annotations.SerializedName;

public class SquadPlayer {
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("position_id")
    private Long positionId;
    @SerializedName("jersey_number")
    private Long jerseyNumber;
    private String start;
    private String end;
    private Boolean captain;
    @SerializedName("transfer_id")
    private Long transferId;

    public SquadPlayer() {
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public Long getJerseyNumber() {
        return jerseyNumber;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public Boolean getCaptain() {
        return captain;
    }

    public Long getTransferId() {
        return transferId;
    }
}
