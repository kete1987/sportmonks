package es.com.kete1987.sportmonks.library.core.model.my;

import com.google.gson.annotations.SerializedName;

public class MyUsage {
    @SerializedName("sport_id")
    private Integer sportId;

    private Integer requests;
    private String date;

    public Integer getSportId() { return sportId; }
    public Integer getRequests() { return requests; }
    public String getDate() { return date; }
}
