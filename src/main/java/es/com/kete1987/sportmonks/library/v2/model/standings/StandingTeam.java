package es.com.kete1987.sportmonks.library.v2.model.standings;

import es.com.kete1987.sportmonks.library.v2.model.team.Team;

public class StandingTeam {
    private final int position = -1;
    private final int team_id = -1;
    private final String team_name = null;
    private final int group_id = -1;
    private final String group_name = null;
    private final StandingInfo overall = null;
    private final StandingInfo home = null;
    private final StandingInfo away = null;
    private final StandingInfoTotal total = null;
    private final String result = null;
    private final int points = 0;
    private final String recent_form = null;
    private final String status = null;
    private final Team team = null;

    public StandingTeam() {
    }

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
