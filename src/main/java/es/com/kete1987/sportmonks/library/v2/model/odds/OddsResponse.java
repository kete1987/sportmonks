package es.com.kete1987.sportmonks.library.v2.model.odds;

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
