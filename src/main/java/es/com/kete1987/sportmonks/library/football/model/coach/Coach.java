package es.com.kete1987.sportmonks.library.football.model.coach;

public class Coach {
    private Long id;
    private Long sport_id;
    private Long country_id;
    private Long nationality_id;
    private String name;
    private String firstname;
    private String lastname;
    private String display_name;
    private String image_path;
    private String date_of_birth;
    private String gender;

    public Coach() {
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

    public Long getNationalityId() {
        return nationality_id;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getImagePath() {
        return image_path;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public String getGender() {
        return gender;
    }
}
