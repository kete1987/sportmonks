package es.com.kete1987.sportmonks.library.v2.model.league;

import es.com.kete1987.sportmonks.library.v2.model.season.SeasonDataList;
import es.com.kete1987.sportmonks.library.v2.model.season.SeasonDataResponse;

public class League {
    private final int id = -1;
    private final String name = null;
    private final boolean is_cup = false;
    private final long current_season_id = -1;
    private final int current_round_id = -1;
    private final int current_stage_id = -1;
    private final boolean live_standings = false;
    private final String logo_path = null;
    private SeasonDataResponse season;
    private SeasonDataList seasons;
    private final Country country = null;

    public League() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCup() {
        return is_cup;
    }

    public long getCurrentSeasonId() {
        return current_season_id;
    }

    public int getCurrentRoundId() {
        return current_round_id;
    }

    public int getCurrentStageId() {
        return current_stage_id;
    }

    public SeasonDataResponse getCurrentSeason() {
        return season;
    }

    public SeasonDataList getListOfSeasons() {
        return seasons;
    }

    public boolean hasLiveStandings() {
        return live_standings;
    }

    public Country getCountry() {
        return country;
    }

    public String getLogoPath() {
        return logo_path;
    }
}
