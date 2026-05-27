package es.com.kete1987.sportmonks.library.odds.model;

import com.google.gson.annotations.SerializedName;

public class Market {
    private Long id;
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
