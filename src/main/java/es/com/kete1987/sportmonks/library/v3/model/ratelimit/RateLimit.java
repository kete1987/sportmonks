package es.com.kete1987.sportmonks.library.v3.model.ratelimit;

public class RateLimit {
    private Long resets_in_seconds;
    private Long remaining;
    private String requested_entity;

    public RateLimit () {}

    public Long getResets_in_seconds() {
        return resets_in_seconds;
    }

    public void setResets_in_seconds(Long resets_in_seconds) {
        this.resets_in_seconds = resets_in_seconds;
    }

    public Long getRemaining() {
        return remaining;
    }

    public void setRemaining(Long remaining) {
        this.remaining = remaining;
    }

    public String getRequested_entity() {
        return requested_entity;
    }

    public void setRequested_entity(String requested_entity) {
        this.requested_entity = requested_entity;
    }
}
