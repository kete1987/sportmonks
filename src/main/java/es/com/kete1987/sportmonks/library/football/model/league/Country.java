package es.com.kete1987.sportmonks.library.football.model.league;

import com.google.gson.annotations.SerializedName;

public class Country {
    private Long id;
    @SerializedName("continent_id")
    private Long continentId;
    private String name;
    @SerializedName("official_name")
    private String officialName;
    @SerializedName("fifa_name")
    private String fifaName;
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

    public String getImagePath() {
        return imagePath;
    }
}
