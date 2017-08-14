package pro.soccerama.library.model;

public class Stats 
{
	private int team_id = 0;
	private int fixture_id = 0;
	private Shots shots = null;
	private Passes passes = null;
	private int fouls = 0;
	private int corners = 0;
	private int offsides = 0;
	private int possessiontime = 0;
	private int yellowcards = 0;
	private int redcards = 0;
	private int saves = 0;
	
	public Stats() {}

	public int getTeamId() {
		return team_id;
	}

	public int getFixtureId() {
		return fixture_id;
	}

	public Shots getShots() {
		return shots;
	}

	public Passes getPasses() {
		return passes;
	}

	public int getFouls() {
		return fouls;
	}

	public int getCorners() {
		return corners;
	}

	public int getOffsides() {
		return offsides;
	}

	public int getPossessiontime() {
		return possessiontime;
	}

	public int getYellowcards() {
		return yellowcards;
	}

	public int getRedcards() {
		return redcards;
	}

	public int getSaves() {
		return saves;
	}
}
