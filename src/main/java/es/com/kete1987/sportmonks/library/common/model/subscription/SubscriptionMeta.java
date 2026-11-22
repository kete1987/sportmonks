package es.com.kete1987.sportmonks.library.common.model.subscription;

import com.google.gson.annotations.SerializedName;

public class SubscriptionMeta {
    @SerializedName("trial_ends_at")
    private String trialEndsAt;
    @SerializedName("ends_at")
    private String endsAt;
    @SerializedName("current_timestamp")
    private Long currentTimestamp;

    public SubscriptionMeta() {
    }

    public String getTrialEndsAt() {
        return trialEndsAt;
    }

    public void setTrialEndsAt(String trialEndsAt) {
        this.trialEndsAt = trialEndsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    /** Server time of the response, in epoch seconds. */
    public Long getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(Long currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }
}
