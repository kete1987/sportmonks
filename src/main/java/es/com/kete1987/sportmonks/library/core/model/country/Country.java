package es.com.kete1987.sportmonks.library.core.model.country;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import es.com.kete1987.sportmonks.library.core.model.continent.Continent;
import es.com.kete1987.sportmonks.library.core.model.region.Region;

import java.util.List;

public class Country {
    private Long id;
    @SerializedName("continent_id")
    private Long continentId;
    private String name;
    @SerializedName("official_name")
    private String officialName;
    @SerializedName("fifa_name")
    private String fifaName;
    private String iso2;
    private String iso3;
    private String latitude;
    private String longitude;
    private List<String> borders;
    @SerializedName("image_path")
    private String imagePath;
    private Continent continent;
    private List<Region> regions;

    public Country() {
    }

    /** ISO3 codes of the neighbouring countries. Always present, it is not an include. */
    public List<String> getBorders() {
        return ModelCollections.unmodifiable(borders);
    }

    /** Needs {@code include=continent}. */
    public Continent getContinent() {
        return continent;
    }

    /** Needs {@code include=regions}. */
    public List<Region> getRegions() {
        return ModelCollections.unmodifiable(regions);
    }

    public Long getId() {
        return id;
    }

    public Long getContinentId() {
        return continentId;
    }

    public String getName() {
        return name;
    }

    public String getOfficialName() {
        return officialName;
    }

    public String getFifaName() {
        return fifaName;
    }

    public String getIso2() {
        return iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getImagePath() {
        return imagePath;
    }
}
