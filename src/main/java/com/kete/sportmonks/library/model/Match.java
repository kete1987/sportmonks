package com.kete.sportmonks.library.model;

public class Match 
{
	protected Long id; //ID del partido
	//Equipos
	protected int localteam_id = -1;
	protected int visitorteam_id = -1;
	protected MatchTeam localTeam = null;
	protected MatchTeam visitorTeam =  null;
	//Resultado
	protected MatchScore scores = null;
	protected MatchTime time = null;
	protected TeamFormation formations = null;
	//Otros datos
	protected int league_id = -1;
	protected int season_id = -1;
	protected int round_id =  -1;
	protected int stage_id =  -1;
	protected boolean deleted = false;
	protected int venue_id = -1;
	
	
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

	public TeamFormation getFormations() {
		return formations;
	}

	public int getVenueId() {
		return venue_id;
	}
	
	public int getRoundId() {
		return round_id;
	}
	
	public int getStageId() {
		return stage_id;
	}

	public MatchTeam getLocalTeam() {
		return localTeam;
	}

	public MatchTeam getVisitorTeam() {
		return visitorTeam;
	}
	
}
