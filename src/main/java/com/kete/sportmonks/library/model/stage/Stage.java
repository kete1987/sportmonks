package com.kete.sportmonks.library.model.stage;

import com.kete.sportmonks.library.model.match.MatchDataList;
import com.kete.sportmonks.library.model.match.MatchDetail;

import java.util.List;

public class Stage 
{
	private int id = -1;
	private String name = null;
	private String type = null;
	private int league_id = -1;
	private int season_id = -1;
	private MatchDataList fixtures = null;
	
	public Stage() {}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getLeagueId() {
		return league_id;
	}

	public int getSeasonId() {
		return season_id;
	}

	public List<MatchDetail> getFixtures() {
		if (fixtures != null)
			return fixtures.getMatchDetailList();
		else return null;
	}
}
