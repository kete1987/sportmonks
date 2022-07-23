package es.com.kete1987.sportmonks.library.model.group;

import es.com.kete1987.sportmonks.library.model.match.MatchDataList;
import es.com.kete1987.sportmonks.library.model.standings.Standings;

public class Group
{
	private String name = null;
	private int league_id = -1;
	private int season_id = -1;
	private int stage_id = -1;
	private String stage_name = null;
	private MatchDataList fixtures = null;
	private Standings standings = null;
	
	public Group() {}

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

	public MatchDataList getFixtures() {
		return fixtures;
	}

	public Standings getStandings() {
		return standings;
	}
	
	
}
