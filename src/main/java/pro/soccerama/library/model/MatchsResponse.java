package pro.soccerama.library.model;

import java.util.List;

public class MatchsResponse 
{
	private List<Match> data;
	private Metadata meta;
	
	public MatchsResponse() {}
	
	public List<Match> getListOfMatches()
	{
		return data;
	}
	
	public Metadata getMetadata()
	{
		return meta;
	}
}
