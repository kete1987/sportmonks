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

    public String getShortName() {
        return short_name;
    }

    public Boolean getIsLive() {
        return is_live;
    }

    public Boolean getIsPending() {
        return is_pending;
    }

    public Boolean getIsPeriodEnd() {
        return is_period_end;
    }

    public Boolean getIsFinalState() {
        return is_final_state;
    }

    public Boolean getIsCancelled() {
        return is_cancelled;
    }

    public Boolean getIsFinalStandingState() {
        return is_final_standing_state;
    }

    public Boolean getIsCompleted() {
        return is_completed;
    }

    public Boolean getIsDeleted() {
        return is_deleted;
    }

    public Boolean getIsNotStarted() {
        return is_notstarted;
    }

    public Long getTypeId() {
        return type_id;
    }
}
