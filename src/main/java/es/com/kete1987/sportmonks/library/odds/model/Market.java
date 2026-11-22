package es.com.kete1987.sportmonks.library.odds.model;

import com.google.gson.annotations.SerializedName;

public class Market {
    private Long id;
    @SerializedName("legacy_id")
    private Long legacyId;
    private String name;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("has_winning_calculations")
    private Boolean hasWinningCalculations;
    @SerializedName("developer_name")
    private String developerName;

    public Market() {
    }

    public Long getId() {
        return id;
    }

    /** @return the id of the market in the API v2, or null when it has none. */
    public Long getLegacyId() {
        return legacyId;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Boolean getHasWinningCalculations() {
        return hasWinningCalculations;
    }

    public String getDeveloperName() {
        return developerName;
    }
}
