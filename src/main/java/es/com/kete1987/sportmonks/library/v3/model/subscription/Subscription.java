package es.com.kete1987.sportmonks.library.v3.model.subscription;

public class Subscription {
    private SubscriptionMeta meta;
    private SubscriptionPlanList plans;

    public Subscription() {}

    public SubscriptionMeta getMeta() {
        return meta;
    }

    public void setMeta(SubscriptionMeta meta) {
        this.meta = meta;
    }

    public SubscriptionPlanList getPlans() {
        return plans;
    }

    public void setPlans(SubscriptionPlanList plans) {
        this.plans = plans;
    }
}
