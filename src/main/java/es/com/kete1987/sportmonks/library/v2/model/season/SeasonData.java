package es.com.kete1987.sportmonks.library.v2.model.season;

import es.com.kete1987.sportmonks.library.v2.model.group.Group;
import es.com.kete1987.sportmonks.library.v2.model.group.GroupsData;
import es.com.kete1987.sportmonks.library.v2.model.league.League;
import es.com.kete1987.sportmonks.library.v2.model.league.LeagueData;
import es.com.kete1987.sportmonks.library.v2.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.v2.model.match.MatchsResponse;
import es.com.kete1987.sportmonks.library.v2.model.stage.Stage;
import es.com.kete1987.sportmonks.library.v2.model.stage.StagesData;

import java.util.List;

public class SeasonData {
    private final int id = -1;
    private final String name = null;
    private final int league_id = -1;
    private final boolean is_current_season = false;
    private final int current_round_id = -1;
    private final int current_stage_id = -1;
    private final MatchsResponse results = null;
    private final LeagueData league = null;
    private final StagesData stages = null;
    private final GroupsData groups = null;

    public SeasonData() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLeagueId() {
        return league_id;
    }

    public boolean isCurrentSeason() {
        return is_current_season;
    }

    public int getCurrentRoundId() {
        return current_round_id;
    }

    public int getCurrentStageId() {
        return current_stage_id;
    }

    public List<MatchDetail> getListOfMatches() {
        if (results != null)
            return results.getListOfMatches();
        else return null;
    }

    public League getLeague() {
        if (league != null)
            return league.getData();
        else return null;
    }

    public List<Stage> getListOfStages() {
        if (stages != null)
            return stages.getListOfStages();
        else return null;
    }

    public List<Group> getListOfGroups() {
        if (groups != null)
            return groups.getListOfGroups();
        else return null;
    }
}
