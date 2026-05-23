package es.com.kete1987.sportmonks.library.core.model.my;

import com.google.gson.annotations.SerializedName;

public class MyResource {
    private String name;
    private String endpoint;

    @SerializedName("sport_id")
    private Integer sportId;

    public String getName() { return name; }
    public String getEndpoint() { return endpoint; }
    public Integer getSportId() { return sportId; }
}
