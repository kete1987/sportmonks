package es.com.kete1987.sportmonks.library.v3.model.match;

public class StatisticsData {
    private Long id;
    private Long fixture_id;
    private Long type_id;
    private Long participant_id;
    private StatisticsDataValue data;
    private String location;

    public StatisticsData() {}

    public Long getId() {
        return id;
    }

    public Long getFixture_id() {
        return fixture_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public Long getParticipant_id() {
        return participant_id;
    }

    public StatisticsDataValue getData() {
        return data;
    }

    public String getLocation() {
        return location;
    }
}
