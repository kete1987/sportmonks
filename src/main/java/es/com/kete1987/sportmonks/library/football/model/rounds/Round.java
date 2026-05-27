package es.com.kete1987.sportmonks.library.football.model.rounds;

import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class Round {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("stage_id")
    private Long stageId;
    private String name;
    private Boolean finished;
    @SerializedName("is_current")
    private Boolean isCurrent;
    @SerializedName("starting_at")
    private String startingAt;
    @SerializedName("ending_at")
    private String endingAt;
    @SerializedName("games_in_current_week")
    private Boolean gamesInCurrentWeek;
    private List<MatchDetail> fixtures;

    public Round() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Long getStageId() {
        return stageId;
    }

    public String getName() {
        return name;
    }

    public Boolean getFinished() {
        return finished;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public String getStartingAt() {
        return startingAt;
    }

    public String getEndingAt() {
        return endingAt;
    }

    public Boolean getGamesInCurrentWeek() {
        return gamesInCurrentWeek;
    }

    public List<MatchDetail> getFixtures() {
        return ModelCollections.unmodifiable(fixtures);
    }
}
