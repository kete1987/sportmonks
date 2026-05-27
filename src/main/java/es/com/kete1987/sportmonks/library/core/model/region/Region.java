package es.com.kete1987.sportmonks.library.core.model.region;

import com.google.gson.annotations.SerializedName;

public class Region {
    private Long id;
    @SerializedName("country_id")
    private Long countryId;
    private String name;

    public Region() {
    }

    public Long getId() {
        return id;
    }

    public Long getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }
}
