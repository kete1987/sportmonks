package es.com.kete1987.sportmonks.library.core.model.my;

import com.google.gson.annotations.SerializedName;

/** Request count of one endpoint in one rate-limit period, as listed by {@code my/usage}. */
public class MyUsage {
    private Long id;
    private String endpoint;
    private Integer count;
    private String entity;
    @SerializedName("remaining_requests")
    private Integer remainingRequests;
    @SerializedName("period_start")
    private Long periodStart;
    @SerializedName("period_end")
    private Long periodEnd;

    public Long getId() {
        return id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Integer getCount() {
        return count;
    }

    public String getEntity() {
        return entity;
    }

    public Integer getRemainingRequests() {
        return remainingRequests;
    }

    /** Start of the period, in epoch seconds. */
    public Long getPeriodStart() {
        return periodStart;
    }

    /** End of the period, in epoch seconds. */
    public Long getPeriodEnd() {
        return periodEnd;
    }
}