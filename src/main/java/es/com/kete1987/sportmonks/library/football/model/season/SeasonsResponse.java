package es.com.kete1987.sportmonks.library.football.model.season;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class SeasonsResponse {
    private List<SeasonData> data;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;
    private Pagination pagination;

    public SeasonsResponse() {
    }

    public List<SeasonData> getData() {
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

    public Pagination getPagination() {
        return pagination;
    }
}
