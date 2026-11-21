package es.com.kete1987.sportmonks.library.football.model.venue;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

public class Venue {
    private Long id;
    @SerializedName("country_id")
    private Long countryId;
    @SerializedName("city_id")
    private Long cityId;
    private String name;
    private String address;
    private String zipcode;
    private String latitude;
    private String longitude;
    private Long capacity;
    @SerializedName("image_path")
    private String imagePath;
    @SerializedName("city_name")
    private String cityName;
    private String surface;
    @SerializedName("national_team")
    private Boolean nationalTeam;

    public Venue() {
    }

    public Long getId() {
        return id;
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

    public String getAddress() {
        return address;
    }

    /**
     * @return the postal code as the API sends it. It is alphanumeric in many countries
     *         ({@code "CV6 6GE"}, {@code "1234 AB"}), which is why it is a string
     *         (it used to be a {@code Long} and made Gson throw; see MIGRATION.md); null when unknown.
     */
    @Nullable
    public String getZipcode() {
        return zipcode;
    }

    /** @return the latitude in decimal degrees, as the API sends it (a string), or null. */
    @Nullable
    public String getLatitude() {
        return latitude;
    }

    /** @return the longitude in decimal degrees, as the API sends it (a string), or null. */
    @Nullable
    public String getLongitude() {
        return longitude;
    }

    public Long getCapacity() {
        return capacity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCityName() {
        return cityName;
    }

    /** @return the playing surface ({@code "grass"}, {@code "artificial"}, ...), or null. */
    @Nullable
    public String getSurface() {
        return surface;
    }

    /** @return true when the venue is a national team's ground, or null when the API omits it. */
    @Nullable
    public Boolean getNationalTeam() {
        return nationalTeam;
    }
}
