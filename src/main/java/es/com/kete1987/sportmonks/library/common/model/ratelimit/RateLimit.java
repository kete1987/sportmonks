package es.com.kete1987.sportmonks.library.common.model.ratelimit;

import com.google.gson.annotations.SerializedName;

public class RateLimit {
    @SerializedName("resets_in_seconds")
    private Long resetsInSeconds;
    private Long remaining;
    @SerializedName("requested_entity")
    private String requestedEntity;

    public RateLimit() {
    }

    public Long getResetsInSeconds() {
        return resetsInSeconds;
    }

    public void setResetsInSeconds(Long resetsInSeconds) {
        this.resetsInSeconds = resetsInSeconds;
    }

    public Long getRemaining() {
        return remaining;
    }

    public void setRemaining(Long remaining) {
        this.remaining = remaining;
    }

    public String getRequestedEntity() {
        return requestedEntity;
    }

    public void setRequestedEntity(String requestedEntity) {
        this.requestedEntity = requestedEntity;
    }
}
