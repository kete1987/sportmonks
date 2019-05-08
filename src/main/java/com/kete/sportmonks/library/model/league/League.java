package com.kete.sportmonks.library.model.league;

import com.kete.sportmonks.library.model.season.SeasonDataList;
import com.kete.sportmonks.library.model.season.SeasonDataResponse;

public class League
{
	private int id = -1;
	private String name = null;
	private boolean is_cup = false;
	private long current_season_id = -1;
	private int current_round_id = -1;
	private int current_stage_id = -1;
	private boolean live_standings = false;
	private String logo_path = null;
	private SeasonDataResponse season;
	private SeasonDataList seasons;
	private Country country = null;
	
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

	public long getCurrentSeasonId() {
		return current_season_id;
	}

	public int getCurrentRoundId() {
		return current_round_id;
	}

	public int getCurrentStageId() {
		return current_stage_id;
	}

	public SeasonDataResponse getCurrentSeason() { return season; }

	public SeasonDataList getListOfSeasons() { return seasons;}

	public boolean hasLiveStandings() {
		return live_standings;
	}

	public Country getCountry() {
		return country;
	}

	public String getLogoPath() {
		return logo_path;
	}
}
