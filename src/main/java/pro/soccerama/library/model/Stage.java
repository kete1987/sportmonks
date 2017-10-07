package pro.soccerama.library.model;

import java.util.List;

public class Stage 
{
	private int id = -1;
	private String name = null;
	private int league_id = -1;
	private MatchDataList fixtures = null;
	
	public Stage() {}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getLeague_id() {
		return league_id;
	}

	public List<MatchDetail> getFixtures() {
		if (fixtures != null)
			return fixtures.getMatchDetailList();
		else return null;
	}
}
