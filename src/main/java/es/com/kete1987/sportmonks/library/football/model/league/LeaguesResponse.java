package es.com.kete1987.sportmonks.library.football.model.league;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class LeaguesResponse {
    private List<League> data;
    private Pagination pagination;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public LeaguesResponse() {
    }

    public List<League> getData() {
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
