package es.com.kete1987.sportmonks.library.football.model.squad;

public class SquadPlayer {
    private Long player_id;
    private Long team_id;
    private Long position_id;
    private Long jersey_number;
    private String start;
    private String end;
    private Boolean captain;
    private Long transfer_id;

    public SquadPlayer() {
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

    public Long getJerseyNumber() {
        return jersey_number;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public Boolean getCaptain() {
        return captain;
    }

    public Long getTransferId() {
        return transfer_id;
    }
}
