package com.kete.sportmonks.library.model;

public class TopScoresPlayer 
{
	private int position = -1;
	private int goals = -1;
	private int penalty_goals = -1;
	private PlayerData player = null;
	private Team team = null;
	
	public TopScoresPlayer() {}

	public int getPosition() {
		return position;
	}

	public int getGoals() {
		return goals;
	}

	public int getPenaltyGoals() {
		return penalty_goals;
	}

	public PlayerData getPlayer() {
		return player;
	}

	public Team getTeam() {
		return team;
	}	
}
