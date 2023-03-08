package es.com.kete1987.sportmonks.library.v2.model.standings;

public class StandingInfo 
{
	private int games_played = 0;
	private int won = 0;
	private int draw = 0;
	private int lost = 0;
	private int goals_scored = 0;
	private int goals_against = 0;
	
	public StandingInfo(){}

	public int getGamesPlayed() {
		return games_played;
	}

	public int getWon() {
		return won;
	}

	public int getDraw() {
		return draw;
	}

	public int getLost() {
		return lost;
	}

	public int getGoalsScored() {
		return goals_scored;
	}

	public int getGoalsAgainst() {
		return goals_against;
	}
}
