package com.kete.sportmonks.library.model;

public class Stats 
{
	private int team_id = 0;
	private int fixture_id = 0;
	private Shots shots = null;
	private Passes passes = null;
	//TODO attacks
	private int fouls = 0;
	private int corners = 0;
	private int offsides = 0;
	private int possessiontime = 0;
	private int yellowcards = 0;
	private int redcards = 0;
	private int saves = 0;
	private int substitutions = 0;
	private int goal_kick;
	private int goal_attempts = 0;
	private int free_kick;
	private int throw_in;
	private int ball_safe = 0;
	
	public Stats() {}

	public int getTeamId() {
		return team_id;
	}

	public int getFixtureId() {
		return fixture_id;
	}

	public Shots getShots() {
		return shots;
	}

	public Passes getPasses() {
		return passes;
	}

	public int getFouls() {
		return fouls;
	}

	public int getCorners() {
		return corners;
	}

	public int getOffsides() {
		return offsides;
	}

	public int getPossessiontime() {
		return possessiontime;
	}

	public int getYellowcards() {
		return yellowcards;
	}

	public int getRedcards() {
		return redcards;
	}

	public int getSaves() {
		return saves;
	}

	public int getSubstitutions() {
		return substitutions;
	}

	public int getGoal_kick() {
		return goal_kick;
	}

	public int getGoal_attempts() {
		return goal_attempts;
	}

	public int getFree_kick() {
		return free_kick;
	}

	public int getThrow_in() {
		return throw_in;
	}

	public int getBall_safe() {
		return ball_safe;
	}
}
