package com.kete.sportmonks.library.model.player;

public class Player 
{
	//Lineup Attributes
	private int team_id = -1;
	private int fixture_id = -1;
	private int player_id = -1;
	private String player_name = null;
	private int number = -1;
	private String position = null;
	private String additional_position = null;
	private String formation_position = null;
	private Integer posx;
	private Integer posy;
	private boolean captain;
	//Player Attributes
	private String common_name = null;
	private String display_name = null;
	private String image_path = null;
	private String logo_path = null;
	private int country_id = -1;
	private String fullname = null;
	private String nationality = null;
	
	private PlayerData player = null; 
	
	public Player() {}

	public int getMatchId() {
		return fixture_id;
	}

	public int getTeamId() {
		return team_id;
	}

	public int getPlayerId() {
		return player_id;
	}

	public String getPlayerName() {
		return player_name;
	}

	public String getPosition() {
		return position;
	}

	public String getAdditionalPosition() {
		return additional_position;
	}

	public String getFormationPosition() {
		return formation_position;
	}

	public Integer getPosx() {
		return posx;
	}

	public Integer getPosy() {
		return posy;
	}

	public boolean isCaptain() {
		return captain;
	}

	public int getShirtNumber() {
		return number;
	}
	
	public String getPlayerCommonName() {
		return common_name;
	}
	
	public String getImagePath() {
		if (image_path != null)
			return image_path;
		else if (logo_path != null)
			return logo_path;
		else return null;
	}

	public int getCountryId() {
		return country_id;
	}

	public String getFullname() {
		return fullname;
	}

	public String getNationality() {
		return nationality;
	}

	public Player getPlayer() {
		return player != null ? player.getData() : null;
	}

	public String getDisplayName() {
		return display_name;
	}
}
