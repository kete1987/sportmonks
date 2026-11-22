package es.com.kete1987.sportmonks.library.core.model.city;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.core.model.region.Region;

public class City {
    private Long id;
    @SerializedName("region_id")
    private Long regionId;
    @SerializedName("country_id")
    private Long countryId;
    private String name;
    private String latitude;
    private String longitude;
    private Region region;

    public City() {
    }

    /** Needs {@code include=region}. */
    public Region getRegion() {
        return region;
    }

    public Long getId() {
        return id;
    }

    public Long getRegionId() {
        return regionId;
    }

    public Long getCountryId() {
        return countryId;
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
