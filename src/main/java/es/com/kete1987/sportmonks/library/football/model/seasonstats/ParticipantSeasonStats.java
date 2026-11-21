package es.com.kete1987.sportmonks.library.football.model.seasonstats;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

/**
 * Index entry of the {@code statistics} include of a team, player, coach or referee: it says for which
 * season statistics exist ({@code has_values}) without carrying the values. Only the id of the owner
 * that matches the requested entity is filled in.
 */
public class ParticipantSeasonStats {
    private Long id;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("coach_id")
    private Long coachId;
    @SerializedName("referee_id")
    private Long refereeId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("has_values")
    private Boolean hasValues;
    @SerializedName("position_id")
    private Long positionId;
    @SerializedName("jersey_number")
    private Long jerseyNumber;

    public Long getId() {
        return id;
    }

    /** @return the team, or null when the entry does not belong to a team scope. */
    @Nullable
    public Long getTeamId() {
        return teamId;
    }

    /** @return the player, or null when the entry belongs to another entity. */
    @Nullable
    public Long getPlayerId() {
        return playerId;
    }

    /** @return the coach, or null when the entry belongs to another entity. */
    @Nullable
    public Long getCoachId() {
        return coachId;
    }

    /** @return the referee, or null when the entry belongs to another entity. */
    @Nullable
    public Long getRefereeId() {
        return refereeId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Boolean getHasValues() {
        return hasValues;
    }

    /** @return the position the player held that season (player entries only), or null. */
    @Nullable
    public Long getPositionId() {
        return positionId;
    }

    /** @return the shirt number that season (player entries only), or null. */
    @Nullable
    public Long getJerseyNumber() {
        return jerseyNumber;
    }
}