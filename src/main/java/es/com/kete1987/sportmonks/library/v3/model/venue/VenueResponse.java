package es.com.kete1987.sportmonks.library.v3.model.venue;

import es.com.kete1987.sportmonks.library.v3.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.v3.model.subscription.Subscription;

import java.util.List;

public class VenueResponse {
    private List<Venue> data;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public VenueResponse() {
    }

    public List<Venue> getData() {
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
