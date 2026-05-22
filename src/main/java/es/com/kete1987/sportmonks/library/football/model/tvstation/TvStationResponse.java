package es.com.kete1987.sportmonks.library.football.model.tvstation;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class TvStationResponse {
    private TvStation data;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public TvStationResponse() {
    }

    public TvStation getData() {
        return data;
    }

    public List<Subscription> getSubscription() {
        return subscription;
    }

    public RateLimit getRateLimit() {
        return rate_limit;
    }

    public String getTimezone() {
        return timezone;
    }
}
