package com.kete.sportmonks.library.model.season;

import com.kete.sportmonks.library.model.Metadata;

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
