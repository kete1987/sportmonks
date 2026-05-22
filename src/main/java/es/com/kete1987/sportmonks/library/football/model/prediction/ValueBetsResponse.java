package es.com.kete1987.sportmonks.library.football.model.prediction;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class ValueBetsResponse {

    private List<ValueBet> data;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public ValueBetsResponse() {
    }

    public List<ValueBet> getData() {
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
