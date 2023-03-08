package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v3.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.v3.model.subscription.SubscriptionData;

public class MatchData
{
	//private MatchDetail data;
	private SubscriptionData subscription;
	private RateLimit rate_limit;
	private String timezone;

	public MatchData() {}

//	public MatchDetail getMatchDetail()
//	{
//		return data;
//	}


	public SubscriptionData getSubscription() {
		return subscription;
	}

	public RateLimit getRateLimit() {
		return rate_limit;
	}

	public String getTimezone() {
		return timezone;
	}
}
