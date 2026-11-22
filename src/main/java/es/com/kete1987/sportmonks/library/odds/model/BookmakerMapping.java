package es.com.kete1987.sportmonks.library.odds.model;

import com.google.gson.annotations.SerializedName;

/** How a bookmaker identifies a fixture on its own side ({@code bookmakers/fixtures/{id}/mapping}). */
public class BookmakerMapping {
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("bookmaker_id")
    private Long bookmakerId;
    @SerializedName("bookmaker_name")
    private String bookmakerName;
    @SerializedName("bookmaker_event_id")
    private String bookmakerEventId;
    @SerializedName("bookmaker_event_url")
    private String bookmakerEventUrl;

    public BookmakerMapping() {
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getBookmakerId() {
        return bookmakerId;
    }

    public String getBookmakerName() {
        return bookmakerName;
    }

    /** @return the id of the fixture inside the bookmaker's own system. */
    public String getBookmakerEventId() {
        return bookmakerEventId;
    }

    /** @return the link to the fixture on the bookmaker's site, or null when the bookmaker has none. */
    public String getBookmakerEventUrl() {
        return bookmakerEventUrl;
    }
}
