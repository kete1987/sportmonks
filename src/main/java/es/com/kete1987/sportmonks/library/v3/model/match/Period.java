package es.com.kete1987.sportmonks.library.v3.model.match;

public class Period {
    private Long id;
    private Long fixture_id;
    private Long type_id;
    private Long started;
    private Long ended;
    private Long counts_from;
    private Boolean ticking;
    private Long sort_order;
    private String description;
    private Long time_added;
    private Long period_length;
    private Long minutes;
    private Long seconds;

    public Period() {}

    public Long getId() {
        return id;
    }

    public Long getFixture_id() {
        return fixture_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public Long getStarted() {
        return started;
    }

    public Long getEnded() {
        return ended;
    }

    public Long getCounts_from() {
        return counts_from;
    }

    public Boolean getTicking() {
        return ticking;
    }

    public Long getSort_order() {
        return sort_order;
    }

    public String getDescription() {
        return description;
    }

    public Long getTime_added() {
        return time_added;
    }

    public Long getPeriod_length() {
        return period_length;
    }

    public Long getMinutes() {
        return minutes;
    }

    public Long getSeconds() {
        return seconds;
    }
}
