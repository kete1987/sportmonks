package es.com.kete1987.sportmonks.library.v2.model.standings;

import java.util.List;

public class StandingsData 
{
	private List<StandingsDataInfo> data = null;
	
	public StandingsData() {}
	
	public List<StandingsDataInfo> getStandingsDataInfo() {
		return data;
	}
	
	public Standings getStandings() {
		if (data != null && data.size() > 0)
			return data.get(0).getStandings();
		else return null;
	}
}
