package es.com.kete1987.sportmonks.library.football.model.player;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;

public class PlayerResponse {
    @SerializedName("data")
    private Player player;
    private List<Subscription> subscription;
    private RateLimit rate_limit;
    private String timezone;

    public PlayerResponse() {
    }

    public Player getData() {
        return player;
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
