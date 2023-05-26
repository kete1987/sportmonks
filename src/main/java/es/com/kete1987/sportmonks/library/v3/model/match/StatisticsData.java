package es.com.kete1987.sportmonks.library.v3.model.match;

public class StatisticsData {
    private Long id;
    private Long fixture_id;
    private Long type_id;
    private Long participant_id;
    private StatisticsDataValue data;
    private String location;

    public StatisticsData() {
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

    public Long getParticipantId() {
        return participant_id;
    }

    public StatisticsDataValue getData() {
        return data;
    }

    public String getLocation() {
        return location;
    }
}
