package es.com.kete1987.sportmonks.library.football.model.prediction;

import com.google.gson.annotations.SerializedName;

public class ValueBet {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("bookmaker_id")
    private Long bookmakerId;
    @SerializedName("market_id")
    private Long marketId;
    private Double odds;
    private Double value;
    private String label;
    @SerializedName("fair_odds")
    private Double fairOdds;
    @SerializedName("created_at")
    private String createdAt;

    public ValueBet() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getBookmakerId() {
        return bookmakerId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public Double getOdds() {
        return odds;
    }

    public Double getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public Double getFairOdds() {
        return fairOdds;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
