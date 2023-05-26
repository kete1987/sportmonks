package es.com.kete1987.sportmonks.library.v2.model.team;

import com.google.gson.annotations.SerializedName;

public class TeamFormation {
    @SerializedName("localteam_formation")
    private String localTeamFormation;
    @SerializedName("visitorteam_formation")
    private String visitorTeamFormation;

    public TeamFormation() {
    }

    public String getLocalTeamFormation() {
        return localTeamFormation;
    }

    public String getVisitorTeamFormation() {
        return visitorTeamFormation;
    }
}
