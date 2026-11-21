package es.com.kete1987.sportmonks.library.football.model.team;

import com.google.gson.annotations.SerializedName;

/** A ranking position of a team (for example UEFA), as returned by the team {@code rankings} include. */
public class TeamRankingEntry {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("participant_id")
    private Long participantId;
    private Long position;
    private Long points;
    private String type;

    public TeamRankingEntry() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public Long getPosition() {
        return position;
    }

    public Long getPoints() {
        return points;
    }

    /** @return the ranking scheme ({@code "UEFA"}, ...). */
    public String getType() {
        return type;
    }
}