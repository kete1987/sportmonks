package es.com.kete1987.sportmonks.library.v3.model.league;

public class Country {
    private Long id;
    private Long continent_id;
    private String name;
    private String official_name;
    private String fifa_name;
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

    public String getImagePath() {
        return image_path;
    }
}
