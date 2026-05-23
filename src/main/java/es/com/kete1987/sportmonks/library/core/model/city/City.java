package es.com.kete1987.sportmonks.library.core.model.city;

public class City {
    private Long id;
    private Long region_id;
    private Long country_id;
    private String name;
    private String latitude;
    private String longitude;

    public City() {
    }

    public Long getId() {
        return id;
    }

    public Long getRegionId() {
        return region_id;
    }

    public Long getCountryId() {
        return country_id;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
