package es.com.kete1987.sportmonks.library.common.model.subscription;

import java.util.List;
import java.util.Collections;

public class Subscription {
    private SubscriptionMeta meta;
    private List<SubscriptionPlan> plans;

    public Subscription() {
    }

    public SubscriptionMeta getMeta() {
        return meta;
    }

    public void setMeta(SubscriptionMeta meta) {
        this.meta = meta;
    }

    public List<SubscriptionPlan> getPlans() {
        return plans == null ? null : Collections.unmodifiableList(plans);
    }

    public void setPlans(List<SubscriptionPlan> plans) {
        this.plans = plans;
    }
}
