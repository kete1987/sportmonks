package es.com.kete1987.sportmonks.library.football.model.rounds;

import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.football.model.league.League;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.seasonstats.ModelStatistic;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import org.jetbrains.annotations.Nullable;
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
    private League league;
    private SeasonData season;
    private Stage stage;
    private List<ModelStatistic> statistics;

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

    /** @return the league, or null if the {@code league} include is absent. */
    @Nullable
    public League getLeague() {
        return league;
    }

    /** @return the season, or null if the {@code season} include is absent. */
    @Nullable
    public SeasonData getSeason() {
        return season;
    }

    /** @return the stage, or null if the {@code stage} include is absent. */
    @Nullable
    public Stage getStage() {
        return stage;
    }

    /** @return the round statistics, or null if the {@code statistics} include is absent. */
    @Nullable
    public List<ModelStatistic> getStatistics() {
        return ModelCollections.unmodifiable(statistics);
    }
}
