package es.com.kete1987.sportmonks.library.football.model.trophy;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

/** A trophy won by a team, player or coach, as returned by the {@code trophies} include. */
public class Trophy {
    private Long id;
    @SerializedName("participant_id")
    private Long participantId;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("trophy_id")
    private Long trophyId;

    public Long getId() {
        return id;
    }

    /** @return the id of the team, player or coach that won it. */
    public Long getParticipantId() {
        return participantId;
    }

    /** @return the team the participant won it with (players and coaches), or null. */
    @Nullable
    public Long getTeamId() {
        return teamId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    /** @return the id of the trophy type (for example 1 for the champion), see {@code core/types}. */
    public Long getTrophyId() {
        return trophyId;
    }
}