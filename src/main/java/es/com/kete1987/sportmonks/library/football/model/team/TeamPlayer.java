package es.com.kete1987.sportmonks.library.football.model.team;

import es.com.kete1987.sportmonks.library.football.model.player.Player;
import com.google.gson.annotations.SerializedName;

public class TeamPlayer {
    private Long id;
    @SerializedName("transfer_id")
    private Long transferId;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("position_id")
    private Long positionId;
    @SerializedName("detailed_position_id")
    private Long detailedPositionId;
    @SerializedName("jersey_number")
    private Long jerseyNumber;
    private String start;
    private String end;
    private Player player;

    public TeamPlayer() {
    }

    public Long getId() {
        return id;
    }

    public Long getTransferId() {
        return transferId;
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

    public Long getDetailedPositionId() {
        return detailedPositionId;
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

    /**
     * @return the nested player (name, display name, image, …) when the request
     * was made with the {@code players.player} include; {@code null} otherwise
     * (the bare {@code players} include only returns the squad pivot).
     */
    public Player getPlayer() {
        return player;
    }
}
