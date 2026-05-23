package es.com.kete1987.sportmonks.library.football.model.totw;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class TeamsOfTheWeekResponse {
    private List<TeamOfTheWeek> data;
    private Pagination pagination;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public TeamsOfTheWeekResponse() {
    }

    public List<TeamOfTheWeek> getData() {
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
