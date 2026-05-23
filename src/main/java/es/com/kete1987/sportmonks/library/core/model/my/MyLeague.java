package es.com.kete1987.sportmonks.library.core.model.my;

public class MyLeague {
    private Long league_id;
    private Long sport_id;
    private String name;
    private String image_path;
    private String type;
    private String sub_type;
    private String last_played_at;

    public MyLeague() {
    }

    public Long getLeagueId() {
        return league_id;
    }

    public Long getSportId() {
        return sport_id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return image_path;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return sub_type;
    }

    public String getLastPlayedAt() {
        return last_played_at;
    }
}
