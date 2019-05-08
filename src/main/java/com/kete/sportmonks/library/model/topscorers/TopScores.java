package com.kete.sportmonks.library.model.topscorers;

import java.util.List;

public class TopScores 
{
	private TopScoresData data;
	
	public TopScores() {}
	
	public TopScoresData getTopScoresData()
	{
		return data;
	}
	
	public List<TopScoresPlayer> getListOfTopScores()
	{
		return data.getListOfTopScores();
	}
}
