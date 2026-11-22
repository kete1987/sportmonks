package es.com.kete1987.sportmonks.library.core.model.my;

import com.google.gson.annotations.SerializedName;

public class MyLeague {
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
    @SerializedName("last_played_at")
    private String lastPlayedAt;
    private Integer category;
    @SerializedName("has_jerseys")
    private Boolean hasJerseys;
    private MySport sport;

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

    public String getLastPlayedAt() {
        return lastPlayedAt;
    }

    public Integer getCategory() {
        return category;
    }

    public Boolean getHasJerseys() {
        return hasJerseys;
    }

    public MySport getSport() {
        return sport;
    }
}