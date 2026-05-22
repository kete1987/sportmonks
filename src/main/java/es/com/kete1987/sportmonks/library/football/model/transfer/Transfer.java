package es.com.kete1987.sportmonks.library.football.model.transfer;

public class Transfer {
    private Long id;
    private Long player_id;
    private Long type_id;
    private Long from_team_id;
    private Long to_team_id;
    private Long position_id;
    private String date;
    private Boolean career_ended;
    private Boolean completed;
    private Long amount;

    public Transfer() {
    }

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return player_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public Long getFromTeamId() {
        return from_team_id;
    }

    public Long getToTeamId() {
        return to_team_id;
    }

    public Long getPositionId() {
        return position_id;
    }

    public String getDate() {
        return date;
    }

    public Boolean getCareerEnded() {
        return career_ended;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Long getAmount() {
        return amount;
    }
}
