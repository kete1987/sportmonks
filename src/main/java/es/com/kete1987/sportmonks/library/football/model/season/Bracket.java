package es.com.kete1987.sportmonks.library.football.model.season;

import com.google.gson.annotations.SerializedName;

public class Bracket {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("season_id")
    private Long seasonId;
    private String name;
    @SerializedName("winner_participant_id")
    private Long winnerParticipantId;

    public Long getId() { return id; }
    public Long getSportId() { return sportId; }
    public Long getSeasonId() { return seasonId; }
    public String getName() { return name; }
    public Long getWinnerParticipantId() { return winnerParticipantId; }
}
