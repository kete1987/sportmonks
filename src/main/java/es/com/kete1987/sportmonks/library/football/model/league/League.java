package es.com.kete1987.sportmonks.library.football.model.league;

import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class League {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    private String name;
    private Boolean active;
    @SerializedName("short_code")
    private String shortCode;
    @SerializedName("image_path")
    private String imagePath;
    private String type;
    @SerializedName("sub_type")
    private String subType;
    @SerializedName("last_playerd_at")
    private String lastPlayerdAt;
    @SerializedName("has_jerseys")
    private Boolean hasJerseys;
    private List<SeasonData> seasons;
    private SeasonData currentseason;
    private Country country;

    public League() {
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

    public Boolean getActive() {
        return active;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getLastPlayerdAt() {
        return lastPlayerdAt;
    }

    public Boolean getHasJerseys() {
        return hasJerseys;
    }

    public List<SeasonData> getSeasons() {
        return ModelCollections.unmodifiable(seasons);
    }

    public SeasonData getCurrentseason() {
        return currentseason;
    }

    public Country getCountry() {
        return country;
    }
}
