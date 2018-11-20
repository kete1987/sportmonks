package com.kete.sportmonks.library.model;

import com.google.gson.annotations.SerializedName;
import com.kete.sportmonks.library.model.team_stats.TeamStats;

public class TeamDetail {
    private Long id;
    private String name = null;
    private String logo_path = null;
    @SerializedName("legacy_id")
    private int legacyId = -1;
    @SerializedName("short_code")
    String shortCode;
    String twitter;
    @SerializedName("country_id")
    int countryId = -1;
    @SerializedName("national_team")
    boolean nationalTeam;
    int founded = -1;
    @SerializedName("venue_id")
    int venueId = -1;
    @SerializedName("stats")
    private TeamStats teamStats = null;

    public TeamDetail() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo_path;
    }

    public int getLegacyId() {
        return legacyId;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getTwitter() {
        return twitter;
    }

    public int getCountryId() {
        return countryId;
    }

    public boolean isNationalTeam() {
        return nationalTeam;
    }

    public int getFounded() {
        return founded;
    }

    public int getVenueId() {
        return venueId;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public TeamStats getTeamStats() {
        return teamStats;
    }
}
