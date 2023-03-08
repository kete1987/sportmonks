package es.com.kete1987.sportmonks.library.v2.model.odds;

public class OddBookmaker 
{
	private int id = -1;
	private String name = null;
	private OddData odds = null;
	
	public OddBookmaker() {}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public OddData getOdds() {
		return odds;
	}
	
	
}
