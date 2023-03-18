package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v3.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.v3.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.v3.model.subscription.Subscription;

import java.util.List;

public class MatchsResponse {
    private List<MatchDetail> data;
    private Pagination pagination;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public MatchsResponse() {
    }

    public List<MatchDetail> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
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
