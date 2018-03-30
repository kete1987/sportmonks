package com.kete.sportmonks.library.model;

public class OddInfo 
{
	private String label = null;
	private String value = null;
	private String handicap = null;
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
}
