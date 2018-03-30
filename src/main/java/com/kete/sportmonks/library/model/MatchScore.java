package com.kete.sportmonks.library.model;

public class MatchScore 
{
	private int localteam_score = -1;
	private int visitorteam_score = -1;
	private int localteam_pen_score = -1;
	private int visitorteam_pen_score = -1;
	private String ht_score = null;
	private String ft_score = null;
	private String et_score = null;
	
	public MatchScore() {}

	public int getLocalTeamScore() {
		return localteam_score;
	}

	public int getVisitorTeamScore() {
		return visitorteam_score;
	}

	public int getLocalTeamPenScore() {
		return localteam_pen_score;
	}

	public int getVisitorTeamPenScore() {
		return visitorteam_pen_score;
	}

	public String getHtScore() {
		return ht_score;
	}

	public String getFtScore() {
		return ft_score;
	}

	public String getEtScore() {
		return et_score;
	}
	
	
}
