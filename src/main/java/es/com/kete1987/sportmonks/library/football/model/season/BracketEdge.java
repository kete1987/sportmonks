package es.com.kete1987.sportmonks.library.football.model.season;

import com.google.gson.annotations.SerializedName;

public class BracketEdge {
    private Long id;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("child_fixture_id")
    private Long childFixtureId;
    @SerializedName("child_slot")
    private String childSlot;
    @SerializedName("parent_fixture_id")
    private Long parentFixtureId;
    @SerializedName("parent_outcome")
    private String parentOutcome;

    public BracketEdge() {
    }

    public Long getId() { return id; }
    public Long getSeasonId() { return seasonId; }
    public Long getChildFixtureId() { return childFixtureId; }
    public String getChildSlot() { return childSlot; }
    public Long getParentFixtureId() { return parentFixtureId; }
    public String getParentOutcome() { return parentOutcome; }
}
