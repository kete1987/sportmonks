package es.com.kete1987.sportmonks.library.football.model.referee;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import es.com.kete1987.sportmonks.library.core.model.city.City;
import es.com.kete1987.sportmonks.library.football.model.league.Country;
import es.com.kete1987.sportmonks.library.football.model.seasonstats.ParticipantSeasonStats;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Referee {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    @SerializedName("city_id")
    private Long cityId;
    private String name;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("image_path")
    private String imagePath;
    private String gender;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    @SerializedName("common_name")
    private String commonName;
    private String firstname;
    private String lastname;
    private Long height;
    private Long weight;
    private City city;
    private Country country;
    private Country nationality;
    private List<ParticipantSeasonStats> statistics;

    public Referee() {
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

    public Long getCityId() {
        return cityId;
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

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Nullable
    public String getCommonName() {
        return commonName;
    }

    @Nullable
    public String getFirstname() {
        return firstname;
    }

    @Nullable
    public String getLastname() {
        return lastname;
    }

    /** @return the height in centimetres, or null. */
    @Nullable
    public Long getHeight() {
        return height;
    }

    /** @return the weight in kilograms, or null. */
    @Nullable
    public Long getWeight() {
        return weight;
    }

    /** @return the city, or null if unknown or the {@code city} include is absent. */
    @Nullable
    public City getCity() {
        return city;
    }

    /** @return the country, or null if the {@code country} include is absent. */
    @Nullable
    public Country getCountry() {
        return country;
    }

    /** @return the nationality, or null if the {@code nationality} include is absent. */
    @Nullable
    public Country getNationality() {
        return nationality;
    }

    /** @return the seasons that have statistics, or null if the {@code statistics} include is absent. */
    @Nullable
    public List<ParticipantSeasonStats> getStatistics() {
        return ModelCollections.unmodifiable(statistics);
    }
}
