package es.com.kete1987.sportmonks.library.v3.model.match;

public class State {
    private Long id;
    private String state;
    private String name;
    private String short_name;
    private Boolean is_live;
    private Boolean is_pending;
    private Boolean is_period_end;
    private Boolean is_final_state;
    private Boolean is_cancelled;
    private Boolean is_final_standing_state;
    private Boolean is_completed;
    private Boolean is_deleted;
    private Boolean is_notstarted;
    private Long type_id;

    public State() {}

    public Long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return short_name;
    }

    public Boolean getIs_live() {
        return is_live;
    }

    public Boolean getIs_pending() {
        return is_pending;
    }

    public Boolean getIs_period_end() {
        return is_period_end;
    }

    public Boolean getIs_final_state() {
        return is_final_state;
    }

    public Boolean getIs_cancelled() {
        return is_cancelled;
    }

    public Boolean getIs_final_standing_state() {
        return is_final_standing_state;
    }

    public Boolean getIs_completed() {
        return is_completed;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public Boolean getIs_notstarted() {
        return is_notstarted;
    }

    public Long getType_id() {
        return type_id;
    }
}
