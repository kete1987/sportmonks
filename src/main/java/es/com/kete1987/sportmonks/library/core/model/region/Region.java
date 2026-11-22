package es.com.kete1987.sportmonks.library.core.model.region;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import es.com.kete1987.sportmonks.library.core.model.city.City;
import es.com.kete1987.sportmonks.library.core.model.country.Country;

import java.util.List;

public class Region {
    private Long id;
    @SerializedName("country_id")
    private Long countryId;
    private String name;
    private Country country;
    private List<City> cities;

    public Region() {
    }

    /** Needs {@code include=country}. */
    public Country getCountry() {
        return country;
    }

    /** Needs {@code include=cities}. */
    public List<City> getCities() {
        return ModelCollections.unmodifiable(cities);
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
