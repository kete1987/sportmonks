package es.com.kete1987.sportmonks.library.football.model.coach;

import com.google.gson.annotations.SerializedName;

public class Coach {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    @SerializedName("nationality_id")
    private Long nationalityId;
    private String name;
    private String firstname;
    private String lastname;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("image_path")
    private String imagePath;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    private String gender;

    public Coach() {
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
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }
}
