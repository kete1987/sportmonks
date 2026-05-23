package es.com.kete1987.sportmonks.library.football.model.transfer;

public class TransferRumour {
    private Long id;
    private Long player_id;
    private Long from_team_id;
    private Long to_team_id;
    private String start_date;
    private String end_date;

    public TransferRumour() {
    }

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return player_id;
    }

    public Long getFromTeamId() {
        return from_team_id;
    }

    public Long getToTeamId() {
        return to_team_id;
    }

    public String getStartDate() {
        return start_date;
    }

    public String getEndDate() {
        return end_date;
    }
}
