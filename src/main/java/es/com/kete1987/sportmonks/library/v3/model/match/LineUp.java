package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v2.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class LineUp
{
	private List<Player> data;
	
	public LineUp() {}
	
	public List<Player> getListOfPlayers()
	{
		return data;
	}

	public String getLineUpString(int teamId)
	{
		String lineUp = "";
		for (int i=0; i<data.size(); i++) {
			if (data.get(i).getTeamId() == teamId) {
				if (lineUp.equals(""))
					lineUp = lineUp + data.get(i).getPlayerName();
				else lineUp = lineUp + ", " + data.get(i).getPlayerName();
			}
		}
		return lineUp;		
	}

	public ArrayList<String> getLineUp(int teamId)
	{
		ArrayList<String> lineUp = new ArrayList<String>();
		for (int i=0; i<data.size(); i++) {
			if (data.get(i).getTeamId() == teamId)
				lineUp.add(data.get(i).getPlayerName());
		}
		return lineUp;		
	}
}
