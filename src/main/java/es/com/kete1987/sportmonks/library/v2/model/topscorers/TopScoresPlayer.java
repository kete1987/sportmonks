package es.com.kete1987.sportmonks.library.v2.model.topscorers;

import es.com.kete1987.sportmonks.library.v2.model.player.PlayerData;
import es.com.kete1987.sportmonks.library.v2.model.team.Team;

public class TopScoresPlayer {
    private final int position = -1;
    private final int season_id = -1;
    private final int player_id = -1;
    private final int team_id = -1;
    private final int stage_id = -1;
    private final int goals = -1;
    private final int penalty_goals = -1;
    private final String type = null;
    private final PlayerData player = null;
    private final Team team = null;

    public TopScoresPlayer() {
    }

    public int getPosition() {
        return position;
    }

    public int getSeasonId() {
        return season_id;
    }

    public int getPlayerId() {
        return player_id;
    }

    public int getTeamId() {
        return team_id;
    }

    public int getStageId() {
        return stage_id;
    }

    public int getGoals() {
        return goals;
    }

    public int getPenaltyGoals() {
        return penalty_goals;
    }

    public String getType() {
        return type;
    }

    public PlayerData getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }
}
