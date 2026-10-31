package es.com.kete1987.sportmonks.library.football.model.match;

import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.util.PlayerPosition;
import es.com.kete1987.sportmonks.library.football.util.StatisticsType;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import org.jetbrains.annotations.Nullable;

public class LineUpData {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("position_id")
    private Long positionId;
    @Nullable
    @SerializedName("detailed_position_id")
    private Long detailedPositionId;
    @Nullable
    @SerializedName("formation_field")
    private String formationField;
    @Nullable
    @SerializedName("formation_position")
    private Long formationPosition;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("player_name")
    private String playerName;
    @SerializedName("jersey_number")
    private Long jerseyNumber;
    private Player player;
    public List<LineUpDetails> details;

    public LineUpData() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getFixtureId() {
        return fixtureId;
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

    /** @return the detailed role ID, or null when absent; this is not a tactical location. */
    @Nullable
    public Long getDetailedPositionId() {
        return detailedPositionId;
    }

    /** @return the raw tactical slot (e.g. {@code "2:4"}), or null when unavailable. */
    @Nullable
    public String getFormationField() {
        return formationField;
    }

    /** @return the order within the starting eleven, or null; not a jersey number or coordinate. */
    @Nullable
    public Long getFormationPosition() {
        return formationPosition;
    }

    public String getPosition() {
        if (positionId == null) return "";
        switch (positionId.intValue()) {
            case PlayerPosition.GOALKEEPER: return "P";
            case PlayerPosition.DEFENDER: return "D";
            case PlayerPosition.MIDFIELDER: return "M";
            case PlayerPosition.ATTACKER: return "A";
            default: return "";
        }
    }

    public Long getTypeId() {
        return typeId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDisplayPlayerName() {
        return player != null && player.getDisplayName() != null ? player.getDisplayName() : playerName;
    }

    public Long getJerseyNumber() {
        return jerseyNumber;
    }

    public List<LineUpDetails> getDetails() {
        return ModelCollections.unmodifiable(details);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCaptain() {
        boolean isCaptain = false;
        if (getDetails() != null && !getDetails().isEmpty()) {
            for (LineUpDetails lineUpDetails : getDetails()) {
                if (lineUpDetails.getTypeId() != null && lineUpDetails.getTypeId().intValue() == StatisticsType.CAPTAIN && lineUpDetails.getData() != null && lineUpDetails.getData().getValue() instanceof Boolean) {
                    if (((Boolean) lineUpDetails.getData().getValue()))
                        return true;
                }
            }
        }
        return isCaptain;
    }
}
