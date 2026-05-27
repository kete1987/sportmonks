package es.com.kete1987.sportmonks.library.football.model.comments;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;

public class CommentsResponse {
    private List<Comment> data;
    private List<Subscription> subscription;
    @SerializedName("rate_limit")
    private RateLimit rateLimit;
    private String timezone;

    public CommentsResponse() {
    }

    public List<Comment> getData() {
        return data == null ? null : Collections.unmodifiableList(data);
    }

    public List<Subscription> getSubscription() {
        return subscription == null ? null : Collections.unmodifiableList(subscription);
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }

    public String getTimezone() {
        return timezone;
    }
}
