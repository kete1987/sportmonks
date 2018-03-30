package com.kete.sportmonks.library.model;

public class StandingsDataInfo 
{
	private String name = null;
	private int league_id = -1;
	private int season_id = -1;
	private int stage_id = -1;
	private String stage_name = null;
	private Standings standings;
	
	public StandingsDataInfo() {}

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

	public String getStageName() {
		return stage_name;
	}

	public Standings getStandings() {
		return standings;
	}
}
