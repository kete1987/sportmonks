package com.kete.sportmonks.library.model;

public class MatchTime 
{
	private String status = null;
	private int minute = 0;
	private int extra_minute = 0;
	private int injury_time = 0;
	private int second = 0;
	private int added_time = 0;
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
	
	public int getSecond() {
		return second;
	}
	
	public int getAddedTime() {
		return added_time;
	}
	
	public MatchDate getMatchDate() {
		return starting_at;
	}
}
