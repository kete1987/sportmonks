package es.com.kete1987.sportmonks.library.football.model.referee;

public class Referee {
    private Long id;
    private Long sport_id;
    private Long country_id;
    private Long city_id;
    private String name;
    private String display_name;
    private String image_path;
    private String gender;
    private String date_of_birth;

    public Referee() {
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

    public Long getCityId() {
        return city_id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getImagePath() {
        return image_path;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }
}
