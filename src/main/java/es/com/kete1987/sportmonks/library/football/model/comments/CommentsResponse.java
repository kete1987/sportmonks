package es.com.kete1987.sportmonks.library.football.model.comments;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class CommentsResponse {
    private List<Comment> data;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public CommentsResponse() {
    }

    public List<Comment> getData() {
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
