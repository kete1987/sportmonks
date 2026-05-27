package es.com.kete1987.sportmonks.library.core.model.continent;

import com.google.gson.annotations.SerializedName;

public class Continent {
    private Long id;
    private String name;
    private String code;
    private String flag;
    @SerializedName("image_path")
    private String imagePath;

    public Continent() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getFlag() {
        return flag;
    }

    public String getImagePath() {
        return imagePath;
    }
}
