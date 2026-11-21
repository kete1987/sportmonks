package es.com.kete1987.sportmonks.library.football.model.transfer;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import org.jetbrains.annotations.Nullable;

public class Transfer {
    private Long id;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("from_team_id")
    private Long fromTeamId;
    @SerializedName("to_team_id")
    private Long toTeamId;
    @SerializedName("position_id")
    private Long positionId;
    private String date;
    @SerializedName("career_ended")
    private Boolean careerEnded;
    private Boolean completed;
    private Long amount;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("detailed_position_id")
    private Long detailedPositionId;
    private Player player;
    private Team fromteam;
    private Team toteam;
    private Type type;
    private Type position;
    private Type detailedposition;

    public Transfer() {
    }

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getFromTeamId() {
        return fromTeamId;
    }

    public Long getToTeamId() {
        return toTeamId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public String getDate() {
        return date;
    }

    public Boolean getCareerEnded() {
        return careerEnded;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Long getAmount() {
        return amount;
    }

    @Nullable
    public Long getSportId() {
        return sportId;
    }

    /** @return the id of the detailed position, or null. */
    @Nullable
    public Long getDetailedPositionId() {
        return detailedPositionId;
    }

    /** @return the player, or null if the {@code player} include is absent. */
    @Nullable
    public Player getPlayer() {
        return player;
    }

    /** @return the team the player left, or null if the {@code fromTeam} include is absent. */
    @Nullable
    public Team getFromTeam() {
        return fromteam;
    }

    /** @return the team the player joined, or null if the {@code toTeam} include is absent. */
    @Nullable
    public Team getToTeam() {
        return toteam;
    }

    /** @return the transfer type (loan, permanent, ...), or null if the {@code type} include is absent. */
    @Nullable
    public Type getType() {
        return type;
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
}
