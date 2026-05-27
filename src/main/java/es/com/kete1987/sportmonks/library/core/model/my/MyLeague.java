package es.com.kete1987.sportmonks.library.core.model.my;

import com.google.gson.annotations.SerializedName;

public class MyLeague {
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("sport_id")
    private Long sportId;
    private String name;
    @SerializedName("image_path")
    private String imagePath;
    private String type;
    @SerializedName("sub_type")
    private String subType;
    @SerializedName("last_played_at")
    private String lastPlayedAt;

    public MyLeague() {
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Long getSportId() {
        return sportId;
    }

    public String getName() {
        return name;
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
}
