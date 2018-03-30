package com.kete.sportmonks.library.model;

public class Event 
{
	private int id = -1;
	private int fixture_id = -1;
	private int team_id = -1;
	private int minute = -1;
	private String extra_minute = null;
	private String type = null;	
	private int player_id = -1;
	private int related_player_id = -1;
	private String player_name = null;
	private String related_player_name = null;
	private String reason = null;
	private boolean injuried = false;
	private PlayerData player = null;
	
	public Event() {}

	public int getId() {
		return id;
	}

	public int getMatchId() {
		return fixture_id;
	}

	public int getTeamId() {
		return team_id;
	}

	public int getMinute() {
		return minute;
	}

	public String getExtraMin() {
		return extra_minute;
	}

	public String getType() {
		return type;
	}

	public int getPlayerId() {
		return player_id;
	}
	
	public int getPlayerRelatedId() {
		return related_player_id;
	}
	
	public String getPlayerName() {
		return player_name;
	}

	public String getPlayerRelatedName() {
		return related_player_name;
	}
	
	public String getReason() {
		return reason;
	}
	
	public boolean isInjuried() {
		return injuried;
	}
	
	public Player getPlayer() {
		if (player != null)
			return player.getData();
		else return null;
	}
}
