package es.com.kete1987.sportmonks.library.football.model.tvstation;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import es.com.kete1987.sportmonks.library.football.model.league.Country;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TvStation {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    private String name;
    @SerializedName("image_path")
    private String imagePath;
    private String url;
    private String type;
    @SerializedName("related_id")
    private Long relatedId;
    private List<Country> countries;

    public TvStation() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getUrl() {
        return url;
    }

    /** @return the kind of station ({@code "tv"}, ...), or null. */
    @Nullable
    public String getType() {
        return type;
    }

    /** @return the id of the entity the station is related to, or null. */
    @Nullable
    public Long getRelatedId() {
        return relatedId;
    }

    /** @return the countries where it broadcasts, or null if the {@code countries} include is absent. */
    @Nullable
    public List<Country> getCountries() {
        return ModelCollections.unmodifiable(countries);
    }
}
