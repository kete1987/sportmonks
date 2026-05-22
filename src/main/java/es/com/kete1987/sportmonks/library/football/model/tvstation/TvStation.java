package es.com.kete1987.sportmonks.library.football.model.tvstation;

public class TvStation {
    private Long id;
    private Long sport_id;
    private Long country_id;
    private String name;
    private String image_path;
    private String url;

    public TvStation() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sport_id;
    }

    public Long getCountryId() {
        return country_id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return image_path;
    }

    public String getUrl() {
        return url;
    }
}
