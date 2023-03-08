package es.com.kete1987.sportmonks.library.v2.model.match;

import es.com.kete1987.sportmonks.library.v2.util.MatchStatus;

public class Match implements Comparable<Object>
{
	protected Long id; //ID del partido
	//Equipos
	protected int localteam_id = -1;
	protected int visitorteam_id = -1;
	protected int winner_team_id = -1;
	//Resultado
	protected MatchScore scores = null;
	protected MatchTime time = null;
	//Otros datos
	protected int attendance = 0;
	protected int league_id = -1;
	protected int season_id = -1;
	protected int stage_id = -1;
	protected int round_id = -1;
	protected long aggregate_id = -1;
	protected String leg = null;
	protected boolean deleted = false;
	
	public Match() {}

	public Long getId() {
		return id;
	}

	public int getLocalTeamId() {
		return localteam_id;
	}

	public int getVisitorTeamId() {
		return visitorteam_id;
	}

	public MatchScore getMatchScore() {
		return scores;
	}

	public MatchTime getMatchTime() {
		return time;
	}

	public int getLeagueId() {
		return league_id;
	}

	public int getSeasonId() {
		return season_id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public int getStageId() {
		return stage_id;
	}

	public int getRoundId() {
		return round_id;
	}

	public int getWinnerTeamId() {
		return winner_team_id;
	}

	public int getAttendance() {
		return attendance;
	}

	public long getAggregateId() {
		return aggregate_id;
	}

	public String getLeg() {
		return leg;
	}

	@Override
	public int compareTo(Object o) {
		Match aux = (Match)o;
		if (getMatchTime() != null && aux.getMatchTime() != null) {
			String status1 = getMatchTime().getStatus();
			String status2 = aux.getMatchTime().getStatus();
			if (status1.equals(MatchStatus.NOT_STARTED)) {
				if (status2.equals(MatchStatus.NOT_STARTED))
					return (int)(getMatchTime().getMatchDate().getTimestamp() - aux.getMatchTime().getMatchDate().getTimestamp());
				else if (MatchStatus.isLiveMatch(status2))
					return 1;
				else
					return -1;
			}
			else if (MatchStatus.isLiveMatch(status1)) {
				if (MatchStatus.isLiveMatch(status2))
					return 0;
				else
					return -1;
			}
			else if (MatchStatus.isMatchFinished(status1)) {
				if (status2.equals(MatchStatus.NOT_STARTED) || MatchStatus.isLiveMatch(status2))
					return 1;
				else if (MatchStatus.isMatchFinished(status2))
					return 0;
				else
					return -1;
			}
			else {
				if (status2.equals(MatchStatus.NOT_STARTED) || MatchStatus.isLiveMatch(status2) || MatchStatus.isMatchFinished(status2))
					return 1;
				else
					return 0;
			}
		}
		return 0;
	}
}
