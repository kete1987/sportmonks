package com.kete.sportmonks.library.model;

public class SeasonData {
    private long id = -1;
    private String name = null;
    private int league_id = -1;
    private boolean is_current_season = false;
    private int current_round_id = -1;
    private int current_stage_id = -1;


    public SeasonData() {
    }

    public long getId() {
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

}
