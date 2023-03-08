package es.com.kete1987.sportmonks.library.v3.model.subscription;

public class SubscriptionMeta {
    private String trial_ends_at;
    private String ends_at;

    public SubscriptionMeta() {}

    public String getTrial_ends_at() {
        return trial_ends_at;
    }

    public void setTrial_ends_at(String trial_ends_at) {
        this.trial_ends_at = trial_ends_at;
    }

    public String getEnds_at() {
        return ends_at;
    }

    public void setEnds_at(String ends_at) {
        this.ends_at = ends_at;
    }
}
