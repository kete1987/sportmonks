package es.com.kete1987.sportmonks.library.v3.model.team;

import es.com.kete1987.sportmonks.library.v3.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.v3.model.subscription.Subscription;

import java.util.List;

public class TeamResponse {
    private Team data;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public TeamResponse() {}

    public Team getData() {
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
