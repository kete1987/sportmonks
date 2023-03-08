package es.com.kete1987.sportmonks.library.v3.model.match;

public class Match implements Comparable<Object>
{
	protected Long id; //ID del partido
	protected Long sport_id;
	protected Long league_id;
	protected Long season_id;
	protected Long stage_id;
	protected Long group_id;
	protected Long aggregate_id;
	protected Long round_id;
	protected Long state_id;
	protected Long venue_id;
	protected String name;
	protected String starting_at;
	protected String leg;
	protected Long length;
	protected Long starting_at_timestamp;

	public Match() {}

	public Long getId() {
		return id;
	}

	public Long getSport_id() {
		return sport_id;
	}

	public Long getLeague_id() {
		return league_id;
	}

	public Long getSeason_id() {
		return season_id;
	}

	public Long getStage_id() {
		return stage_id;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public Long getAggregate_id() {
		return aggregate_id;
	}

	public Long getRound_id() {
		return round_id;
	}

	public Long getState_id() {
		return state_id;
	}

	public Long getVenue_id() {
		return venue_id;
	}

	public String getName() {
		return name;
	}

	public String getStarting_at() {
		return starting_at;
	}

	public String getLeg() {
		return leg;
	}

	public Long getLength() {
		return length;
	}

	public Long getStarting_at_timestamp() {
		return starting_at_timestamp;
	}

	//TODO
	@Override
	public int compareTo(Object o) {
	/*	Match aux = (Match)o;
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
		return 0;*/
		return 0;
	}
}
