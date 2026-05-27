package es.com.kete1987.sportmonks.library.football.model.transfer;

import com.google.gson.annotations.SerializedName;

public class TransferRumour {
    private Long id;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("from_team_id")
    private Long fromTeamId;
    @SerializedName("to_team_id")
    private Long toTeamId;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;

    public TransferRumour() {
    }

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getFromTeamId() {
        return fromTeamId;
    }

    public Long getToTeamId() {
        return toTeamId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
