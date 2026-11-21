package es.com.kete1987.sportmonks.library.football.model.team;

import com.google.gson.annotations.SerializedName;

/** A spell of a coach at a team, as returned by the team {@code coaches} include. */
public class TeamCoach {
    private Long id;
    @SerializedName("team_id")
    private Long teamId;
    @SerializedName("coach_id")
    private Long coachId;
    @SerializedName("position_id")
    private Long positionId;
    private Boolean active;
    private String start;
    private String end;
    private Boolean temporary;

    public Long getId() {
        return id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getCoachId() {
        return coachId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public Boolean getActive() {
        return active;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public Boolean getTemporary() {
        return temporary;
    }
}