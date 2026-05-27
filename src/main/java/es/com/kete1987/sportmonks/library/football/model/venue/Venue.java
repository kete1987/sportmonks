package es.com.kete1987.sportmonks.library.football.model.venue;

import com.google.gson.annotations.SerializedName;

public class Venue {
    private Long id;
    @SerializedName("country_id")
    private Long countryId;
    @SerializedName("city_id")
    private Long cityId;
    private String name;
    private String address;
    private Long zipcode;
    private Long capacity;
    @SerializedName("image_path")
    private String imagePath;
    @SerializedName("city_name")
    private String cityName;

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

    public Long getZipcode() {
        return zipcode;
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
}
