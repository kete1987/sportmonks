package es.com.kete1987.sportmonks.library.football.model.league;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import org.jetbrains.annotations.Nullable;

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

    public Country() {
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

    /** @return the ISO 3166-1 alpha-2 code, or null. */
    @Nullable
    public String getIso2() {
        return iso2;
    }

    /** @return the ISO 3166-1 alpha-3 code, or null. */
    @Nullable
    public String getIso3() {
        return iso3;
    }

    /** @return the latitude of the country centre, as the API sends it (a string), or null. */
    @Nullable
    public String getLatitude() {
        return latitude;
    }

    /** @return the longitude of the country centre, as the API sends it (a string), or null. */
    @Nullable
    public String getLongitude() {
        return longitude;
    }

    /** @return the ISO3 codes of the neighbouring countries, or null when the API omits them. */
    @Nullable
    public List<String> getBorders() {
        return ModelCollections.unmodifiable(borders);
    }

    public String getImagePath() {
        return imagePath;
    }
}
