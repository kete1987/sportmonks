package es.com.kete1987.sportmonks.library.football.model.tvstation;

import com.google.gson.annotations.SerializedName;

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
}
