package es.com.kete1987.sportmonks.library.v3.model.odds;

public class Odd {
    private Long id;
    private Long fixture_id;
    private Long market_id;
    private Long bookmaker_id;
    private String label;
    private String value;
    private String name;
    private String market_description;
    private String probability;
    private String dp3;
    private String fractional;
    private String american;
    private Boolean winning;
    private Boolean stopped;
    private String total;
    private String handicap;
    private String participants;
    private String created_at;
    private String updated_at;
    private String original_label;
    private String latest_bookmaker_update;

    public Odd() {}

    public Long getId() {
        return id;
    }

    public Long getFixture_id() {
        return fixture_id;
    }

    public Long getMarket_id() {
        return market_id;
    }

    public Long getBookmaker_id() {
        return bookmaker_id;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getMarket_description() {
        return market_description;
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

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getOriginal_label() {
        return original_label;
    }

    public String getLatest_bookmaker_update() {
        return latest_bookmaker_update;
    }
}
