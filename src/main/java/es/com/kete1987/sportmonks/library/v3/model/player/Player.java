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

    public Long getSport_id() {
        return sport_id;
    }

    public Long getCountry_id() {
        return country_id;
    }

    public Long getNationality_id() {
        return nationality_id;
    }

    public Long getCity_id() {
        return city_id;
    }

    public Long getPosition_id() {
        return position_id;
    }

    public Long getDetailed_position_id() {
        return detailed_position_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public String getCommon_name() {
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

    public String getDisplay_name() {
        return display_name;
    }

    public String getImage_path() {
        return image_path;
    }

    public Long getHeight() {
        return height;
    }

    public Long getWeight() {
        return weight;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getGender() {
        return gender;
    }
}
