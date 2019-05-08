package com.kete.sportmonks.library.model.topscorers;

import com.kete.sportmonks.library.model.player.PlayerData;
import com.kete.sportmonks.library.model.team.Team;

public class TopScoresPlayer
{
	private int position = -1;
	private int season_id = -1;
	private int player_id = -1;
	private int team_id = -1;
	private int stage_id = -1;
	private int goals = -1;
	private int penalty_goals = -1;
	private String type = null;
	private PlayerData player = null;
	private Team team = null;
	
	public TopScoresPlayer() {}

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
