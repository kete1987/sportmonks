package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v2.model.Metadata;

import java.util.List;

public class MatchsResponse 
{
	private List<MatchDetail> data;
	private Metadata meta;
	
	public MatchsResponse() {}
	
	public List<MatchDetail> getListOfMatches()
	{
		return data;
	}
	
	public Metadata getMetadata()
	{
		return meta;
	}
}
