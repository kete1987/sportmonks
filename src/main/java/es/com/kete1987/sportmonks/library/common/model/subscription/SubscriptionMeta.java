package es.com.kete1987.sportmonks.library.common.model.subscription;

import com.google.gson.annotations.SerializedName;

public class SubscriptionMeta {
    @SerializedName("trial_ends_at")
    private String trialEndsAt;
    @SerializedName("ends_at")
    private String endsAt;

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
}
