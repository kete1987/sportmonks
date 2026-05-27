package es.com.kete1987.sportmonks.library.football.model.season;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class SeasonDataResponse {
    @SerializedName("data")
    private SeasonData seasonData;
    private List<Subscription> subscription;
    @SerializedName("rate_limit")
    private RateLimit rateLimit;
    private String timezone;

    public SeasonDataResponse() {
    }

    public SeasonData getSeasonData() {
        return seasonData;
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
