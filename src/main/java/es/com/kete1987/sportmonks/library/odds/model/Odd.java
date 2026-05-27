package es.com.kete1987.sportmonks.library.odds.model;

import com.google.gson.annotations.SerializedName;

public class Odd {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("market_id")
    private Long marketId;
    @SerializedName("bookmaker_id")
    private Long bookmakerId;
    private String label;
    private String value;
    private String name;
    @SerializedName("market_description")
    private String marketDescription;
    private String probability;
    private String dp3;
    private String fractional;
    private String american;
    private Boolean winning;
    private Boolean stopped;
    private String total;
    private String handicap;
    private String participants;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("original_label")
    private String originalLabel;
    @SerializedName("latest_bookmaker_update")
    private String latestBookmakerUpdate;
    private Bookmaker bookmaker;

    public Odd() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public Long getBookmakerId() {
        return bookmakerId;
    }

    public Bookmaker getBookmaker() { return bookmaker; }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getMarketDescription() {
        return marketDescription;
    }

    public String getProbability() {
        return probability;
    }

    public String getDp3() {
        return dp3;
    }

    public String getFractional() {
        return fractional;
    }

    public String getAmerican() {
        return american;
    }

    public Boolean getWinning() {
        return winning;
    }

    public Boolean getStopped() {
        return stopped;
    }

    public String getTotal() {
        return total;
    }

    public String getHandicap() {
        return handicap;
    }

    public String getParticipants() {
        return participants;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getOriginalLabel() {
        return originalLabel;
    }

    public String getLatestBookmakerUpdate() {
        return latestBookmakerUpdate;
    }
}
