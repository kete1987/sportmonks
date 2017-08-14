package pro.soccerama.library.model;

import java.util.List;

public class SeasonResults 
{
	private int id = -1;
	private String name = null;
	private int league_id = -1;
	private boolean is_current_season = false;
	private int current_round_id = -1;
	private int current_stage_id = -1;
	private MatchsResponse results = null;
	
	public SeasonResults(){}

	public int getId() {
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
		return results.getListOfMatches();
	}
	
	
}
