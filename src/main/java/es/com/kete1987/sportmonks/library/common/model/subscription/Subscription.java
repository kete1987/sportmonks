package es.com.kete1987.sportmonks.library.common.model.subscription;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class Subscription {
    private SubscriptionMeta meta;
    private List<SubscriptionPlan> plans;
    /** Add-ons of the plan; the API sends them as free-form objects. */
    @SerializedName("add_ons")
    private List<Map<String, Object>> addOns;
    /** Widgets of the plan; the API sends them as free-form objects. */
    private List<Map<String, Object>> widgets;

    public Subscription() {
    }

    public SubscriptionMeta getMeta() {
        return meta;
    }

    public void setMeta(SubscriptionMeta meta) {
        this.meta = meta;
    }

    public List<SubscriptionPlan> getPlans() {
        return ModelCollections.unmodifiable(plans);
    }

    public void setPlans(List<SubscriptionPlan> plans) {
        this.plans = plans;
    }

    public List<Map<String, Object>> getAddOns() {
        return ModelCollections.unmodifiable(addOns);
    }

    public void setAddOns(List<Map<String, Object>> addOns) {
        this.addOns = addOns;
    }

    public List<Map<String, Object>> getWidgets() {
        return ModelCollections.unmodifiable(widgets);
    }

    public void setWidgets(List<Map<String, Object>> widgets) {
        this.widgets = widgets;
    }
}
