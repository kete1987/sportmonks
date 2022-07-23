package es.com.kete1987.sportmonks.library.model.season;

import es.com.kete1987.sportmonks.library.model.Metadata;

public class SeasonDataResponse
{
	private SeasonData data = null;
	private Metadata meta;
	
	public SeasonDataResponse(){}
	
	public SeasonData getData()
	{
		return data;
	}

	public Metadata getMetadata() {
		return meta;
	}
}
