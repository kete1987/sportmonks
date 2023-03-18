package es.com.kete1987.sportmonks.library.v3.model.subscription;

public class SubscriptionMeta {
    private String trial_ends_at;
    private String ends_at;

    public SubscriptionMeta() {
    }

    public String getTrialEndsAt() {
        return trial_ends_at;
    }

    public void setTrialEndsAt(String trial_ends_at) {
        this.trial_ends_at = trial_ends_at;
    }

    public String getEndsAt() {
        return ends_at;
    }

    public void setEndsAt(String ends_at) {
        this.ends_at = ends_at;
    }
}
