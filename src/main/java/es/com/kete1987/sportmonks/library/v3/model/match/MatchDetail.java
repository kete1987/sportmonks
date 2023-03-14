package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v3.model.team.Team;
import es.com.kete1987.sportmonks.library.v3.model.venue.Venue;
import es.com.kete1987.sportmonks.library.v3.util.MatchStatus;

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

	public Team getHomeTeam() {
		for (Team team : getParticipants()) {
			if (team.getMeta() != null && team.getMeta().getLocation().equals("home"))
				return team;
		}
		return null;
	}

	public Team getAwayTeam() {
		for (Team team : getParticipants()) {
			if (team.getMeta() != null && team.getMeta().getLocation().equals("away"))
				return team;
		}
		return null;
	}

	public List<Scores> getScores() {
		return scores;
	}

	public int getCurrentLocalTeamGoals() {
		if (getScores() != null) {
			for (Scores score : getScores()) {
				if (score.getDescription().equalsIgnoreCase("CURRENT") && score.getScore() != null && score.getScore().getParticipant().equalsIgnoreCase("home"))
					return score.getScore().getGoals().intValue();
			}
		}
		return 0;
	}

	public int getCurrentVisitorTeamGoals() {
		if (getScores() != null) {
			for (Scores score : getScores()) {
				if (score.getDescription().equalsIgnoreCase("CURRENT") && score.getScore() != null && score.getScore().getParticipant().equalsIgnoreCase("away"))
					return score.getScore().getGoals().intValue();
			}
		}
		return 0;
	}

	public boolean isLiveMatch() {
		return MatchStatus.isLiveMatch(getState().getId().intValue());
	}

	public boolean isMatchFinished() {
		return MatchStatus.isMatchFinished(getState().getId().intValue());
	}

	public Period getCurrentPeriod() {
		if (getPeriods() != null && getPeriods().size() > 0) {
			for (Period period : getPeriods()) {
				if (period.getTypeId().equals(state.getTypeId())) {
					return period;
				}
			}
		}
		return null;
	}
}
