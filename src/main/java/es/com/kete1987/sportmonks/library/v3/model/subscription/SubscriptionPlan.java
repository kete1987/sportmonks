package es.com.kete1987.sportmonks.library.v3.model.subscription;

public class SubscriptionPlan {
    private String plan;
    private String sport;
    private String category;

    public SubscriptionPlan() {
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
