package com.kete.sportmonks.library.model;

import com.google.gson.annotations.SerializedName;

public class TeamFormation {
	@SerializedName("localteam_formation")
    String   localTeamFormation;
	@SerializedName("visitorteam_formation")
    String   visitorTeamFormation;
	
	
	public String getLocalTeamFormation() {
		return localTeamFormation;
	}
	public String getVisitorTeamFormation() {
		return visitorTeamFormation;
	}
	
	
}
