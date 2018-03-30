package com.kete.sportmonks.library.model;

import java.util.List;

public class OddsResponse 
{
	private List<OddType> data = null;
	
	public OddsResponse() {}
	
	public List<OddType> getOddTypes()
	{
		return data;
	}
}
