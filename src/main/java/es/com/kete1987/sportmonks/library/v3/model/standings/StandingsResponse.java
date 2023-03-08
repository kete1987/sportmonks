package es.com.kete1987.sportmonks.library.v3.model.standings;

import es.com.kete1987.sportmonks.library.v3.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.v3.model.subscription.Subscription;

import java.util.List;

public class StandingsResponse {
    private List<Standings> data;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public StandingsResponse() {}

    public List<Standings> getData() {
        return data;
    }

    public List<Subscription> getSubscription() {
        return subscription;
    }

    public RateLimit getRate_limit() {
        return rate_limit;
    }

    public String getTimezone() {
        return timezone;
    }
}
