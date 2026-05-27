package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;

public class State {
    private Long id;
    private String state;
    private String name;
    @SerializedName("short_name")
    private String shortName;
    @SerializedName("developer_name")
    private String developerName;
    private Type type;

    public State() {
    }

    public Long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public Type getType() {
        return type;
    }
}
