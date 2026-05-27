package es.com.kete1987.sportmonks.library.football.model.transfer;

import com.google.gson.annotations.SerializedName;

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
}
