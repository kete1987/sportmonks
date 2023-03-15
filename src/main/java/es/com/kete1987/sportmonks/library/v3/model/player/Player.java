package es.com.kete1987.sportmonks.library.v3.model.player;

public class Player {
    private Long id;
    private Long sport_id;
    private Long country_id;
    private Long nationality_id;
    private Long city_id;
    private Long position_id;
    private Long detailed_position_id;
    private Long type_id;
    private String common_name;
    private String firstname;
    private String lastname;
    private String name;
    private String display_name;
    private String image_path;
    private Long height;
    private Long weight;
    private String date_of_birth;
    private String gender;

    public Player() {}

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

    public Long getCityId() {
        return city_id;
    }

    public Long getPositionId() {
        return position_id;
    }

    public Long getDetailedPositionId() {
        return detailed_position_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public String getCommonName() {
        return common_name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    public Long getHeight() {
        return height;
    }

    public Long getWeight() {
        return weight;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public String getGender() {
        return gender;
    }
}
