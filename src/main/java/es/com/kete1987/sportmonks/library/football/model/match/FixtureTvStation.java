package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;

/**
 * Pivot row of the {@code tvStations} include of a fixture: one TV station broadcasting the fixture
 * in one country. It carries only ids; fetch the station with the {@code tv-stations} endpoints.
 */
public class FixtureTvStation {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("tvstation_id")
    private Long tvStationId;
    @SerializedName("country_id")
    private Long countryId;

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getTvStationId() {
        return tvStationId;
    }

    public Long getCountryId() {
        return countryId;
    }
}
