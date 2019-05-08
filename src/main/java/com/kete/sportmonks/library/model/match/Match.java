package com.kete.sportmonks.library.model.match;

public class Match 
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
}
