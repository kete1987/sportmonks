package es.com.kete1987.sportmonks.library.v3.model.venue;

public class Venue {
    private Long id;
    private Long country_id;
    private Long city_id;
    private String name;
    private String address;
    private Long zipcode;
    private Long capacity;
    private String image_path;
    private String city_name;

    public Venue() {}

    public Long getId() {
        return id;
    }

    public Long getCountry_id() {
        return country_id;
    }

    public Long getCity_id() {
        return city_id;
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

    public String getImage_path() {
        return image_path;
    }

    public String getCity_name() {
        return city_name;
    }
}
