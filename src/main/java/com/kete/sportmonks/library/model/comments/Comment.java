package com.kete.sportmonks.library.model.comments;

public class Comment implements Comparable<Object> {
    private Long fixture_id;
    private Boolean important;
    private Integer order;
    private Boolean goal;
    private Integer minute;
    private Integer extra_minute;
    private String comment;

    public Comment() {}

    public Long getFixtureId() {
        return fixture_id;
    }

    public Boolean getImportant() {
        return important;
    }

    public Integer getOrder() {
        return order;
    }

    public Boolean getGoal() {
        return goal;
    }

    public Integer getMinute() {
        return minute;
    }

    public Integer getExtraMinute() {
        return extra_minute;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int compareTo(Object o) {
        Comment aux = (Comment)o;
        if (getOrder() != null && aux.getOrder() != null) {
            return getOrder() - aux.getOrder();
        }
        else if (getOrder() == null)
            return -1;
        else
            return 1;
    }
}
