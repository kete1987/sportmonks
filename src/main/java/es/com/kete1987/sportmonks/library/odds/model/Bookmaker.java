package es.com.kete1987.sportmonks.library.odds.model;

import com.google.gson.annotations.SerializedName;

public class Bookmaker {
    private Long id;
    @SerializedName("legacy_id")
    private Long legacyId;
    private String name;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("has_streaming")
    private Boolean hasStreaming;
    @SerializedName("is_premium")
    private Boolean isPremium;
    @SerializedName("image_path")
    private String imagePath;

    public Bookmaker() {
    }

    public Long getId() {
        return id;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Boolean getHasStreaming() {
        return hasStreaming;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public String getImagePath() {
        return imagePath;
    }
}
