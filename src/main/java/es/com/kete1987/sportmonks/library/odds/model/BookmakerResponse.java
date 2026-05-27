package es.com.kete1987.sportmonks.library.odds.model;

import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class BookmakerResponse {

    private Bookmaker data;
    private List<Subscription> subscription;
    @SerializedName("rate_limit")
    private RateLimit rateLimit;
    private String timezone;

    public BookmakerResponse() {
    }

    public Bookmaker getData() {
        return data;
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
