package es.com.kete1987.sportmonks.library.football.model.transferrumourconfig;

import com.google.gson.annotations.SerializedName;

/** One value of the transfer rumours catalogue (a fee band, a transfer type or a rumour status). */
public class TransferRumourConfigEntry {
    private Long id;
    private String name;
    private String code;
    @SerializedName("developer_name")
    private String developerName;
    @SerializedName("model_type")
    private String modelType;
    @SerializedName("stat_group")
    private String statGroup;

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
