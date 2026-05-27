package es.com.kete1987.sportmonks.library.football.model.player;

import com.google.gson.annotations.SerializedName;

public class Player {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    @SerializedName("nationality_id")
    private Long nationalityId;
    @SerializedName("city_id")
    private Long cityId;
    @SerializedName("position_id")
    private Long positionId;
    @SerializedName("detailed_position_id")
    private Long detailedPositionId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("common_name")
    private String commonName;
    private String firstname;
    private String lastname;
    private String name;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("image_path")
    private String imagePath;
    private Long height;
    private Long weight;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    private String gender;

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public Long getNationalityId() {
        return nationalityId;
    }

    public Long getCityId() {
        return cityId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public Long getDetailedPositionId() {
        return detailedPositionId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public String getCommonName() {
        return commonName;
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
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getHeight() {
        return height;
    }

    public Long getWeight() {
        return weight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }
}
