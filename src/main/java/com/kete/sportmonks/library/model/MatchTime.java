package com.kete.sportmonks.library.model;

public class MatchTime 
{
	private String status = null;
	private int minute = -1;
	private int extra_minute = -1;
	private int injury_time = -1;
	private MatchDate starting_at = null;
	
	public MatchTime() {}

	public String getStatus() {
		return status;
	}

	public int getMinute() {
		return minute;
	}

	public int getExtraMinute() {
		return extra_minute;
	}

	public int getInjuryTime() {
		return injury_time;
	}	
	
	public MatchDate getMatchDate() {
		return starting_at;
	}
}
