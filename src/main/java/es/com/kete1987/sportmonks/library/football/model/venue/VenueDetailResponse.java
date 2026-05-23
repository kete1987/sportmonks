package es.com.kete1987.sportmonks.library.football.model.venue;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class VenueDetailResponse {
    private Venue data;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public VenueDetailResponse() {
    }

    public Venue getData() {
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
