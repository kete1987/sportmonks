package es.com.kete1987.sportmonks.library.football.model.squad;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import es.com.kete1987.sportmonks.library.football.model.transfer.Transfer;
import org.jetbrains.annotations.Nullable;

public class SquadPlayer {
    private Long id;
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
    private Boolean captain;
    @SerializedName("transfer_id")
    private Long transferId;
    private Player player;
    private Type position;
    private Type detailedposition;
    private Team team;
    private Transfer transfer;

    public SquadPlayer() {
    }

    /** @return the id of the squad entry itself (the pivot between team and player), or null. */
    @Nullable
    public Long getId() {
        return id;
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

    /** @return the id of the detailed position (for example right wing), or null. */
    @Nullable
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

    public Boolean getCaptain() {
        return captain;
    }

    public Long getTransferId() {
        return transferId;
    }

    /** @return the player, or null if the {@code player} include is absent. */
    @Nullable
    public Player getPlayer() {
        return player;
    }

    /** @return the position, or null if the {@code position} include is absent. */
    @Nullable
    public Type getPosition() {
        return position;
    }

    /** @return the detailed position, or null if the {@code detailedPosition} include is absent. */
    @Nullable
    public Type getDetailedPosition() {
        return detailedposition;
    }

    /** @return the team, or null if the {@code team} include is absent. */
    @Nullable
    public Team getTeam() {
        return team;
    }

    /** @return the transfer that brought the player, or null if there is none or the include is absent. */
    @Nullable
    public Transfer getTransfer() {
        return transfer;
    }
}
