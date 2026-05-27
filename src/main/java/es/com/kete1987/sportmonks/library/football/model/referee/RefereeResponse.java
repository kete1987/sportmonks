package es.com.kete1987.sportmonks.library.football.model.referee;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class RefereeResponse {
    @SerializedName("data")
    private Referee referee;
    private List<Subscription> subscription;
    @SerializedName("rate_limit")
    private RateLimit rateLimit;
    private String timezone;

    public RefereeResponse() {
    }

    public Referee getData() {
        return referee;
    }

    public List<Subscription> getSubscription() {
        return ModelCollections.unmodifiable(subscription);
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }

    public String getTimezone() {
        return timezone;
    }
}
