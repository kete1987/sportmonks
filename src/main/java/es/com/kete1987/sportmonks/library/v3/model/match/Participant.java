package es.com.kete1987.sportmonks.library.v3.model.match;

public class Participant {
    private Long id;
    private Long sport_id;
    private Long country_id;
    private Long venue_id;
    private String name;
    private String gender;
    private ParticipantMeta meta;

    public Participant() {}

    public Long getId() {
        return id;
    }

    public Long getSport_id() {
        return sport_id;
    }

    public Long getCountry_id() {
        return country_id;
    }

    public Long getVenue_id() {
        return venue_id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public ParticipantMeta getMeta() {
        return meta;
    }
}
