package com.kete.sportmonks.library.model;

import java.util.List;

public class TopScoresData 
{
	private TopScoresPlayerData goalscorers;
	private int id = -1;
	private String name = null;
	private boolean is_current_season = false;
	
	public TopScoresData() {}
	
	public List<TopScoresPlayer> getListOfTopScores()
	{
		return goalscorers.getListOfTopScores();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isCurrentSeason() {
		return is_current_season;
	}
}
