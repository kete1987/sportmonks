package com.kete.sportmonks.library.model;

import java.util.List;

public class SeasonData 
{
	private long id = -1;
	private String name = null;
	private int league_id = -1;
	private boolean is_current_season = false;
	private int current_round_id = -1;
	private int current_stage_id = -1;
	private MatchsResponse results = null;
	private LeagueData league = null;
	private StagesData stages = null;
	private GroupsData groups = null;
	
	public SeasonData(){}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getLeagueId() {
		return league_id;
	}

	public boolean isCurrentSeason() {
		return is_current_season;
	}

	public int getCurrentRoundId() {
		return current_round_id;
	}

	public int getCurrentStageId() {
		return current_stage_id;
	}

	public List<Match> getListOfMatches() {
		if (results != null)
			return results.getListOfMatches();
		else return null;
	}
	
	public League getLeague() {
		if (league != null)
			return league.getData();
		else return null;
	}
	
	public List<Stage> getListOfStages() {
		if (stages != null)
			return stages.getListOfStages();
		else return null;
	}
	
	public List<Group> getListOfGroups() {
		if (groups != null)
			return groups.getListOfGroups();
		else return null;
	}
}
