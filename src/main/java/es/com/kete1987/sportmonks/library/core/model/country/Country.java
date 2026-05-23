package es.com.kete1987.sportmonks.library.core.model.country;

public class Country {
    private Long id;
    private Long continent_id;
    private String name;
    private String official_name;
    private String fifa_name;
    private String iso2;
    private String iso3;
    private String latitude;
    private String longitude;
    private String image_path;

    public Country() {
    }

    public Long getId() {
        return id;
    }

    public Long getContinentId() {
        return continent_id;
    }

    public String getName() {
        return name;
    }

    public String getOfficialName() {
        return official_name;
    }

    public String getFifaName() {
        return fifa_name;
    }

    public String getIso2() {
        return iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getImagePath() {
        return image_path;
    }
}
