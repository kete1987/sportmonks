package com.kete.sportmonks.library.model;



public class GroupsData {
	private int id;
	private String name = null;
	private int league_id = -1;
	private int season_id = -1;
	private int stage_id = -1;
	private int round_id = -1;
	private int round_name = -1;
	private String stage_name = null;


	public GroupsData() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getLeagueId() {
		return league_id;
	}

	public int getSeasonId() {
		return season_id;
	}

	public int getStageId() {
		return stage_id;
	}

	public int getRoundId() {
		return round_id;
	}

	public int getRoundName() {
		return round_name;
	}

	public String getStageName() {
		return stage_name;
	}
}
