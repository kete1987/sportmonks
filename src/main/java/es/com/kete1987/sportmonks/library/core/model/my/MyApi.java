package es.com.kete1987.sportmonks.library.core.model.my;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class MyApi {
    private Integer sport_id;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public MyApi() {
    }

    public Integer getSportId() {
        return sport_id;
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
