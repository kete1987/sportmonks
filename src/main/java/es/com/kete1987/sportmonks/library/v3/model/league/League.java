package es.com.kete1987.sportmonks.library.v3.model.league;

import es.com.kete1987.sportmonks.library.v3.model.season.SeasonData;

import java.util.List;

public class League {
    private Long id;
    private Long sport_id;
    private Long country_id;
    private String name;
    private Boolean active;
    private String short_code;
    private String image_path;
    private String type;
    private String sub_type;
    private String last_playerd_at;
    private Boolean has_jerseys;
    private List<SeasonData> seasons;
    private SeasonData currentseason;
    private Country country;

    public League() {
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

    public Boolean getActive() {
        return active;
    }

    public String getShortCode() {
        return short_code;
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

    public String getLastPlayerdAt() {
        return last_playerd_at;
    }

    public Boolean getHasJerseys() {
        return has_jerseys;
    }

    public List<SeasonData> getSeasons() {
        return seasons;
    }

    public SeasonData getCurrentseason() {
        return currentseason;
    }

    public Country getCountry() {
        return country;
    }
}
