package com.kete.sportmonks.library.model.match;

import com.kete.sportmonks.library.model.comments.Comment;
import com.kete.sportmonks.library.model.comments.CommentData;
import com.kete.sportmonks.library.model.group.Group;
import com.kete.sportmonks.library.model.odds.OddData;
import com.kete.sportmonks.library.model.stats.Stats;
import com.kete.sportmonks.library.model.stats.StatsData;
import com.kete.sportmonks.library.model.team.Team;
import com.kete.sportmonks.library.model.team.TeamDetail;
import com.kete.sportmonks.library.model.team.TeamFormation;

import java.util.HashMap;
import java.util.List;

public class MatchDetail extends Match
{
	private Team localTeam = null;
	private Team visitorTeam = null;
	private StatsData stats = null;
	private MatchEvents events = null;
	private LineUp lineup = null;
	private LineUp bench = null;
	private Group group = null;
	private TeamFormation formations = null;
	private OddData odds = null;
	private CommentData comments = null;
	
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
		return stats.getStats(getLocalTeamId());
	}

	public Stats getAwayStats() {
		return stats.getStats(getVisitorTeamId());
	}

	public MatchEvents getEvents() {
		return events;
	}
	
	public List<MatchEvent> getListOfEvents() {
		return events.getListOfEvents();
	}

	public LineUp getLineUp() {
		return lineup;
	}

	public LineUp getBench() {
		return bench;
	}
	
	public HashMap<Integer, String> getPlayers() {
		HashMap<Integer, String> playersMap = new HashMap<Integer, String>();
		for (int i=0; i<getLineUp().getListOfPlayers().size(); i++)
			playersMap.put(getLineUp().getListOfPlayers().get(i).getPlayerId(),
							getLineUp().getListOfPlayers().get(i).getPlayer() != null && getLineUp().getListOfPlayers().get(i).getPlayer().getPlayerCommonName() != null ?
							getLineUp().getListOfPlayers().get(i).getPlayer().getPlayerCommonName() : getLineUp().getListOfPlayers().get(i).getPlayerName());
		return playersMap;
	}

	public Group getGroup() {
		return group;
	}

	public TeamFormation getFormations() {
		return formations;
	}

	public OddData getOdds() {
		return odds;
	}

	public List<Comment> getListOfComments() {
		return comments != null ? comments.getListOfComments() : null;
	}
}
