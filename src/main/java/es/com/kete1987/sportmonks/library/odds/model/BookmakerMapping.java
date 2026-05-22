package es.com.kete1987.sportmonks.library.odds.model;

public class BookmakerMapping {
    private Long fixture_id;
    private Long bookmaker_id;
    private String fixture_identifier;

    public BookmakerMapping() {
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getBookmakerId() {
        return bookmaker_id;
    }

    public String getFixtureIdentifier() {
        return fixture_identifier;
    }
}
