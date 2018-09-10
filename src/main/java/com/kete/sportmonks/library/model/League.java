package com.kete.sportmonks.library.model;

public class League 
{
	private int id = -1;
	private String name = null;
	private int country_id = -1;
	private boolean is_cup = false;
	private int current_season_id = -1;
	private int current_round_id = -1;
	private int current_stage_id = -1;
	
	public League() {}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isCup() {
		return is_cup;
	}

	public int getCurrentSeasonId() {
		return current_season_id;
	}

	public int getCurrentRoundId() {
		return current_round_id;
	}

	public int getCurrentStageId() {
		return current_stage_id;
	}
	
	public int getCountryId() {
		return country_id;
	}
	
}
