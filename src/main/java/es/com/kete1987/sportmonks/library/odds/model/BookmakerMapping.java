package es.com.kete1987.sportmonks.library.odds.model;

import com.google.gson.annotations.SerializedName;

public class BookmakerMapping {
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("bookmaker_id")
    private Long bookmakerId;
    @SerializedName("fixture_identifier")
    private String fixtureIdentifier;

    public BookmakerMapping() {
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getBookmakerId() {
        return bookmakerId;
    }

    public String getFixtureIdentifier() {
        return fixtureIdentifier;
    }
}
