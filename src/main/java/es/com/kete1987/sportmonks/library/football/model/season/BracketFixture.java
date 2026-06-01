package es.com.kete1987.sportmonks.library.football.model.season;

import com.google.gson.annotations.SerializedName;

public class BracketFixture {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("stage_id")
    private Long stageId;
    @SerializedName("state_id")
    private Long stateId;
    @SerializedName("venue_id")
    private Long venueId;
    private String name;
    @SerializedName("starting_at")
    private String startingAt;
    @SerializedName("result_info")
    private String resultInfo;
    private String leg;
    private String details;
    private Integer length;
    private Boolean placeholder;
    @SerializedName("has_odds")
    private Boolean hasOdds;
    @SerializedName("has_premium_odds")
    private Boolean hasPremiumOdds;
    @SerializedName("starting_at_timestamp")
    private Long startingAtTimestamp;

    public BracketFixture() {
    }

    public Long getId() { return id; }
    public Long getSportId() { return sportId; }
    public Long getLeagueId() { return leagueId; }
    public Long getSeasonId() { return seasonId; }
    public Long getStageId() { return stageId; }
    public Long getStateId() { return stateId; }
    public Long getVenueId() { return venueId; }
    public String getName() { return name; }
    public String getStartingAt() { return startingAt; }
    public String getResultInfo() { return resultInfo; }
    public String getLeg() { return leg; }
    public String getDetails() { return details; }
    public Integer getLength() { return length; }
    public Boolean getPlaceholder() { return placeholder; }
    public Boolean getHasOdds() { return hasOdds; }
    public Boolean getHasPremiumOdds() { return hasPremiumOdds; }
    public Long getStartingAtTimestamp() { return startingAtTimestamp; }
}
