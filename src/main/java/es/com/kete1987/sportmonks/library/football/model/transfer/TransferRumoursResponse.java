package es.com.kete1987.sportmonks.library.football.model.transfer;

import es.com.kete1987.sportmonks.library.common.model.pagination.Pagination;
import es.com.kete1987.sportmonks.library.common.model.ratelimit.RateLimit;
import es.com.kete1987.sportmonks.library.common.model.subscription.Subscription;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class TransferRumoursResponse {
    private List<TransferRumour> data;
    private Pagination pagination;
    private List<Subscription> subscription;
    @SerializedName("rate_limit")
    private RateLimit rateLimit;
    private String timezone;

    public TransferRumoursResponse() {
    }

    public List<TransferRumour> getData() {
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
