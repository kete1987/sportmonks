package es.com.kete1987.sportmonks.library.football.model.referee;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class RefereesResponse {
    private List<Referee> data;
    private Pagination pagination;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public RefereesResponse() {
    }

    public List<Referee> getData() {
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
