package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v3.model.team.Team;
import es.com.kete1987.sportmonks.library.v3.model.venue.Venue;

import java.util.List;

public class MatchDetail extends Match
{
	private Venue venue;
	private State state;
	private List<LineUpData> lineups;
	private List<EventData> events;
	private List<StatisticsData> statistics;
	private List<Period> periods;
	private List<Team> participants;
	private List<Scores> scores;
	public MatchDetail() {}

	public Venue getVenue() {
		return venue;
	}

	public State getState() {
		return state;
	}

	public List<LineUpData> getLineups() {
		return lineups;
	}

	public List<EventData> getEvents() {
		return events;
	}

	public List<StatisticsData> getStatistics() {
		return statistics;
	}

	public List<Period> getPeriods() {
		return periods;
	}

	public List<Team> getParticipants() {
		return participants;
	}

	public List<Scores> getScores() {
		return scores;
	}
}
