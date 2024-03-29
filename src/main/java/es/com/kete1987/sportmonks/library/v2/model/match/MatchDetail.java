package es.com.kete1987.sportmonks.library.v2.model.match;

import es.com.kete1987.sportmonks.library.v2.model.comments.Comment;
import es.com.kete1987.sportmonks.library.v2.model.comments.CommentData;
import es.com.kete1987.sportmonks.library.v2.model.group.Group;
import es.com.kete1987.sportmonks.library.v2.model.odds.OddData;
import es.com.kete1987.sportmonks.library.v2.model.stats.Stats;
import es.com.kete1987.sportmonks.library.v2.model.stats.StatsData;
import es.com.kete1987.sportmonks.library.v2.model.team.Team;
import es.com.kete1987.sportmonks.library.v2.model.team.TeamDetail;
import es.com.kete1987.sportmonks.library.v2.model.team.TeamFormation;

import java.util.HashMap;
import java.util.List;

public class MatchDetail extends Match {
    private final Team localTeam = null;
    private final Team visitorTeam = null;
    private final StatsData stats = null;
    private final MatchEvents events = null;
    private final LineUp lineup = null;
    private final LineUp bench = null;
    private final Group group = null;
    private final TeamFormation formations = null;
    private final OddData odds = null;
    private final CommentData comments = null;

    public MatchDetail() {
    }

    public TeamDetail getHomeTeam() {
        return localTeam.getTeamDetail();
    }

    public TeamDetail getAwayTeam() {
        return visitorTeam.getTeamDetail();
    }

    public List<Stats> getStats() {
        return stats.getListOfStats();
    }

    public Stats getHomeStats() {
        return stats.getStats(getLocalTeamId());
    }

    public Stats getAwayStats() {
        return stats.getStats(getVisitorTeamId());
    }

    public MatchEvents getEvents() {
        return events;
    }

    public List<MatchEvent> getListOfEvents() {
        return events.getListOfEvents();
    }

    public LineUp getLineUp() {
        return lineup;
    }

    public LineUp getBench() {
        return bench;
    }

    public HashMap<Integer, String> getPlayers() {
        HashMap<Integer, String> playersMap = new HashMap<Integer, String>();
        for (int i = 0; i < getLineUp().getListOfPlayers().size(); i++)
            playersMap.put(getLineUp().getListOfPlayers().get(i).getPlayerId(),
                    getLineUp().getListOfPlayers().get(i).getPlayer() != null && getLineUp().getListOfPlayers().get(i).getPlayer().getDisplayName() != null ?
                            getLineUp().getListOfPlayers().get(i).getPlayer().getDisplayName() : getLineUp().getListOfPlayers().get(i).getPlayerName());
        return playersMap;
    }

    public Group getGroup() {
        return group;
    }

    public TeamFormation getFormations() {
        return formations;
    }

    public OddData getOdds() {
        return odds;
    }

    public List<Comment> getListOfComments() {
        return comments != null ? comments.getListOfComments() : null;
    }
}
