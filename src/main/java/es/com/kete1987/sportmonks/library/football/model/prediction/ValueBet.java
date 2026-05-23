package es.com.kete1987.sportmonks.library.football.model.prediction;

public class ValueBet {
    private Long id;
    private Long fixture_id;
    private Long type_id;
    private Long bookmaker_id;
    private Long market_id;
    private Double odds;
    private Double value;
    private String label;
    private Double fair_odds;
    private String created_at;

    public ValueBet() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public Long getBookmakerId() {
        return bookmaker_id;
    }

    public Long getMarketId() {
        return market_id;
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
        return fair_odds;
    }

    public String getCreatedAt() {
        return created_at;
    }
}
