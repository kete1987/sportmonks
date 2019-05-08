package com.kete.sportmonks.library.model.topscorers;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopScoresData 
{
	@SerializedName(value="scorers", alternate={"goalscorers", "aggregatedGoalscorers"})
	private TopScoresPlayerData scorers;
	private int id = -1;
	private String name = null;
	private int id_league = -1;
	private boolean is_current_season = false;
	
	public TopScoresData() {}
	
	public List<TopScoresPlayer> getListOfTopScores()
	{
		return scorers.getListOfTopScores();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getIdLeague() {
		return id_league;
	}

	public boolean isCurrentSeason() {
		return is_current_season;
	}
}
