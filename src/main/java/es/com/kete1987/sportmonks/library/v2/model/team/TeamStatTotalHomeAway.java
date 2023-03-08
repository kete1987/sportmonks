package es.com.kete1987.sportmonks.library.v2.model.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamStatTotalHomeAway {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("away")
    @Expose
    private String away;

    public TeamStatTotalHomeAway() {}

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }
}
