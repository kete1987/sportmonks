package es.com.kete1987.sportmonks.library.football.model.statistic;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

import java.util.List;

/**
 * Statistics of one player, team, coach or referee in one season, as returned by
 * {@code statistics/seasons/{players|teams|coaches|referees}/{id}}. The values live in
 * {@link #getDetails()}, one {@link Statistic} per type. Only the id of the requested entity is
 * set ({@code playerId}, {@code coachId} or {@code refereeId}); {@code teamId} is also set for
 * players and coaches.
 */
public class SeasonStatistic {
    private Long id;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("team_id")
    private Long teamId;
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
    private Integer jerseyNumber;
    private List<Statistic> details;

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getCoachId() {
        return coachId;
    }

    public Long getRefereeId() {
        return refereeId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Boolean getHasValues() {
        return hasValues;
    }

    /** Position of the player in that season; {@code null} for the other entities. */
    public Long getPositionId() {
        return positionId;
    }

    /** Jersey number of the player in that season; {@code null} for the other entities. */
    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public List<Statistic> getDetails() {
        return ModelCollections.unmodifiable(details);
    }

    /** First detail with the given type id, or {@code null} when the season has none. */
    public Statistic getDetailByTypeId(long typeId) {
        if (details == null) return null;
        for (Statistic detail : details) {
            if (detail.getTypeId() != null && detail.getTypeId() == typeId) return detail;
        }
        return null;
    }
}
