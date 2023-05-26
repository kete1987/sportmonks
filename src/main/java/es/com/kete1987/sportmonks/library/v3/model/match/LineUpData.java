package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v3.util.PlayerPosition;
import es.com.kete1987.sportmonks.library.v3.util.StatisticsType;

import java.util.List;

public class LineUpData {
    private Long id;
    private Long sport_id;
    private Long fixture_id;
    private Long player_id;
    private Long team_id;
    private Long position_id;
    private Long type_id;
    private String player_name;
    private Long jersey_number;
    public List<LineUpDetails> details;

    public LineUpData() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sport_id;
    }

    public Long getFixtureId() {
        return fixture_id;
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

    public String getPosition() {
        switch (position_id.intValue()) {
            case PlayerPosition.GOALKEEPER: return "P";
            case PlayerPosition.DEFENDER: return "D";
            case PlayerPosition.MIDFIELDER: return "M";
            case PlayerPosition.ATTACKER: return "A";
            default: return "";
        }
    }

    public Long getTypeId() {
        return type_id;
    }

    public String getPlayerName() {
        return player_name;
    }

    public Long getJerseyNumber() {
        return jersey_number;
    }

    public List<LineUpDetails> getDetails() {
        return details;
    }

    public boolean isCaptain() {
        boolean isCaptain = false;
        if (getDetails() != null && !getDetails().isEmpty()) {
            for (LineUpDetails lineUpDetails : getDetails()) {
                if (lineUpDetails.getTypeId().intValue() == StatisticsType.CAPTAIN && lineUpDetails.getData() != null && lineUpDetails.getData().getValue() instanceof Boolean) {
                    if (((Boolean) lineUpDetails.getData().getValue()))
                        return true;
                }
            }
        }
        return isCaptain;
    }
}
