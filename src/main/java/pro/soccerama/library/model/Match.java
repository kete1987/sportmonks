package pro.soccerama.library.model;

public class Match 
{
	protected int id = -1; //ID del partido
	//Equipos
	protected int localteam_id = -1;
	protected int visitorteam_id = -1;
	//Resultado
	protected MatchScore scores = null;
	protected MatchTime time = null;
	//Otros datos
	protected int league_id = -1;
	protected int season_id = -1;
	protected boolean deleted = false;
	
	public Match() {}

	public int getId() {
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
}
