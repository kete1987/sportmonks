package es.com.kete1987.sportmonks.library.v2.model.standings;

import es.com.kete1987.sportmonks.library.v2.model.team.Team;

public class StandingTeam
{
	private int position = -1;
	private int team_id = -1;
	private String team_name = null;
	private int group_id = -1;
	private String group_name = null;
	private StandingInfo overall = null;
	private StandingInfo home = null;
	private StandingInfo away = null;
	private StandingInfoTotal total = null;
	private String result = null;
	private int points = 0;
	private String recent_form = null;
	private String status = null;
	private Team team = null;
	
	public StandingTeam(){}

	public int getPosition() {
		return position;
	}

	public int getTeamId() {
		return team_id;
	}

	public String getTeamName() {
		return team_name;
	}

	public int getGroupId() {
		return group_id;
	}

	public String getGroupName() {
		return group_name;
	}

	public StandingInfo getOverall() {
		return overall;
	}

	public StandingInfo getHome() {
		return home;
	}

	public StandingInfo getAway() {
		return away;
	}

	public StandingInfoTotal getTotal() {
		return total;
	}

	public String getResult() {
		return result;
	}

	public int getPoints() {
		return points;
	}

	public String getRecentForm() {
		return recent_form;
	}

	public String getStatus() {
		return status;
	}

	public Team getTeam() {
		return team;
	}
}
