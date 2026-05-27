package es.com.kete1987.sportmonks.library.football.model.player;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class PlayerResponse {
    @SerializedName("data")
    private Player player;
    private List<Subscription> subscription;
    @SerializedName("rate_limit")
    private RateLimit rateLimit;
    private String timezone;

    public PlayerResponse() {
    }

    public Player getData() {
        return player;
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
