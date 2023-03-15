package es.com.kete1987.sportmonks.library.v3.model.team;

public class TeamPlayer {
    private Long id;
    private Long transfer_id;
    private Long player_id;
    private Long team_id;
    private Long position_id;
    private Long detailed_position_id;
    private Long yersey_number;
    private String start;
    private String end;

    public TeamPlayer() {}

    public Long getId() {
        return id;
    }

    public Long getTransferId() {
        return transfer_id;
    }

    public Long getPlayerId() {
        return player_id;
    }

    public Long getTeamId() {
        return team_id;
    }

    public Long getPositionId() {
        return position_id;
    }

    public Long getDetailedPositionId() {
        return detailed_position_id;
    }

    public Long getYerseyNumber() {
        return yersey_number;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
