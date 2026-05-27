package es.com.kete1987.sportmonks.library.football.model.topscorers;

import com.google.gson.annotations.SerializedName;

public class TopScoresType {
    private Long id;
    private String name;
    private String code;
    @SerializedName("developer_name")
    private String developerName;
    @SerializedName("model_type")
    private String modelType;
    @SerializedName("stat_group")
    private String statGroup;

    public TopScoresType() {
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

    public String getDeveloperName() {
        return developerName;
    }

    public String getModelType() {
        return modelType;
    }

    public String getStatGroup() {
        return statGroup;
    }
}
