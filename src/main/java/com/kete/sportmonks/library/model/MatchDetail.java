package com.kete.sportmonks.library.model;

import java.util.HashMap;
import java.util.List;

public class MatchDetail extends Match
{
	private Team localTeam = null;
	private Team visitorTeam = null;
	private StatsData stats = null;
	private MatchEvents events = null;
	private LineUp lineup = null;
	//TODO Odds
	
	public MatchDetail() {}

	public TeamDetail getHomeTeam() {
		return localTeam.getTeamDetail();
	}

	public TeamDetail getAwayTeam() {
		return visitorTeam.getTeamDetail();
	}

	public List<Stats> getStats() {
		return stats.getListOfStats();
	}
	
	public Stats getHomeStats() {
		return stats.getStats(localteam_id);
	}

	public Stats getAwayStats() {
		return stats.getStats(visitorteam_id);
	}

	public MatchEvents getEvents() {
		return events;
	}
	
	public List<Event> getListOfEvents() {
		return events.getListOfEvents();
	}

	public LineUp getLineUp() {
		return lineup;
	}
	
	public HashMap<Integer, String> getPlayers() {
		HashMap<Integer, String> playersMap = new HashMap<Integer, String>();
		for (int i=0; i<getLineUp().getListOfPlayers().size(); i++)
			playersMap.put(getLineUp().getListOfPlayers().get(i).getPlayerId(), getLineUp().getListOfPlayers().get(i).getPlayerName());
		return playersMap;
	}
}
