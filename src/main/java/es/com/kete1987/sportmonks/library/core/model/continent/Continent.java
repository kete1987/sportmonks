package es.com.kete1987.sportmonks.library.core.model.continent;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import es.com.kete1987.sportmonks.library.core.model.country.Country;

import java.util.List;

public class Continent {
    private Long id;
    private String name;
    private String code;
    private String flag;
    @SerializedName("image_path")
    private String imagePath;
    private List<Country> countries;

    public Continent() {
    }

    /** Needs {@code include=countries}. */
    public List<Country> getCountries() {
        return ModelCollections.unmodifiable(countries);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getFlag() {
        return flag;
    }

    public String getImagePath() {
        return imagePath;
    }
}
