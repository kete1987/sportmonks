package es.com.kete1987.sportmonks.library.football.model.player;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class PlayersResponse {
    private List<Player> data;
    private Pagination pagination;
    private List<Subscription> subscription;
    @SerializedName("rate_limit")
    private RateLimit rateLimit;
    private String timezone;

    public PlayersResponse() {
    }

    public List<Player> getData() {
        return ModelCollections.unmodifiable(data);
    }

    public Pagination getPagination() {
        return pagination;
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
