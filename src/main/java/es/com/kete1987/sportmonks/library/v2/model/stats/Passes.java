package es.com.kete1987.sportmonks.library.v2.model.stats;

public class Passes {
    private final Integer total = 0;
    private final Integer accurate = 0;

    public Passes() {
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getAccurate() {
        return accurate;
    }

    public Double getPercentage() {
        return (Double.valueOf(accurate) / Double.valueOf(total)) * 100;
    }
}
