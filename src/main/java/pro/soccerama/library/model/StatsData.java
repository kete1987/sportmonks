package pro.soccerama.library.model;

import java.util.List;

public class StatsData 
{
	private List<Stats> data = null;
	
	public StatsData(){}
	
	public List<Stats> getListOfStats()
	{
		return data;
	}
	
	public Stats getStats(int teamId)
	{
		if (data != null)
		{
			for (int i=0; i<data.size(); i++)
				if (data.get(i).getTeamId() == teamId)
					return data.get(i);
			return null;
		}
		else return null;
	}
}
