package com.kete.sportmonks.library.model;

public class OddInfo 
{
	private String label = null;
	private String value = null;
	private String handicap = null;
	private String total = null;
	private String winning = null;
	private String dp3 = null;
	private OddsLastUpdate last_update = null;
	
	public OddInfo() {}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}
	
	public String getHandicap() {
		return handicap;
	}

	public OddsLastUpdate getLastUpdate() {
		return last_update;
	}

	public String getTotal() {
		return total;
	}

	public String getWinning() {
		return winning;
	}

	public String getDp3() {
		return dp3;
	}

	public OddsLastUpdate getLast_update() {
		return last_update;
	}
	
}
