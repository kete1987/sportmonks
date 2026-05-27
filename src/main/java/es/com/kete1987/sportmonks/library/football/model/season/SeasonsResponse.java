package es.com.kete1987.sportmonks.library.football.model.season;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;

public class SeasonsResponse {
    private List<SeasonData> data;
    private List<Subscription> subscription;
    @SerializedName("rate_limit")
    private RateLimit rateLimit;
    private String timezone;
    private Pagination pagination;

    public SeasonsResponse() {
    }

    public List<SeasonData> getData() {
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

    public Pagination getPagination() {
        return pagination;
    }
}
