package es.com.kete1987.sportmonks.library.football.model.season;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class SeasonDataResponse {
    @SerializedName("data")
    private SeasonData seasonData;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public SeasonDataResponse() {
    }

    public SeasonData getSeasonData() {
        return seasonData;
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
