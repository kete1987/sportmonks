package es.com.kete1987.sportmonks.library.v2.model.standings;

public class StandingsDataInfo 
{
	private Long id;
	private String name = null;
	private int league_id = -1;
	private int season_id = -1;
	private int stage_id = -1;
	private int round_id = -1;
	private String stage_name = null;
	private String resource = null;
	private Standings standings;
	
	public StandingsDataInfo() {}

	public String getName() {
		return name;
	}

	public int getLeagueId() {
		return league_id;
	}

	public int getSeasonId() {
		return season_id;
	}

	public int getStageId() {
		return stage_id;
	}

	public String getStageName() {
		return stage_name;
	}

	public Standings getStandings() {
		return standings;
	}

	public Long getId() {
		return id;
	}

	public int getRoundId() {
		return round_id;
	}

	public String getResource() {
		return resource;
	}
}
