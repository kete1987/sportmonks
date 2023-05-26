package es.com.kete1987.sportmonks.library.v2.model.match;

import es.com.kete1987.sportmonks.library.v2.model.player.Player;
import es.com.kete1987.sportmonks.library.v2.model.player.PlayerData;

public class MatchEvent {
    private final long id = -1;
    private final int fixture_id = -1;
    private final int team_id = -1;
    private final int minute = -1;
    private final int extra_minute = -1;
    private final String type = null;
    private final int player_id = -1;
    private final int related_player_id = -1;
    private final String player_name = null;
    private final String related_player_name = null;
    private final String reason = null;
    private final boolean injuried = false;
    private final String result = null;
    private final PlayerData player = null;
    private final String var_result = null;

    public MatchEvent() {
    }

    public long getId() {
        return id;
    }

    public int getMatchId() {
        return fixture_id;
    }

    public int getTeamId() {
        return team_id;
    }

    public int getMinute() {
        return minute;
    }

    public int getExtraMin() {
        return extra_minute;
    }

    public String getType() {
        return type;
    }

    public int getPlayerId() {
        return player_id;
    }

    public int getPlayerRelatedId() {
        return related_player_id;
    }

    public String getPlayerName() {
        return player_name;
    }

    public String getPlayerRelatedName() {
        return related_player_name;
    }

    public String getReason() {
        return reason;
    }

    public boolean isInjuried() {
        return injuried;
    }

    public String getResult() {
        return result;
    }

    public Player getPlayer() {
        if (player != null)
            return player.getData();
        else return null;
    }

    public String getVarResult() {
        return var_result;
    }
}
