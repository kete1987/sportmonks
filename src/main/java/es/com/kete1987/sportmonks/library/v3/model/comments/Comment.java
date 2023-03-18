package es.com.kete1987.sportmonks.library.v3.model.comments;

public class Comment implements Comparable<Object> {
    private Long id;
    private Long fixture_id;
    private String comment;
    private Long minute;
    private Long extra_minute;
    private Boolean is_goal;
    private Boolean is_important;
    private Long order;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public String getComment() {
        return comment;
    }

    public Long getMinute() {
        return minute;
    }

    public Long getExtraMinute() {
        return extra_minute;
    }

    public Boolean isGoal() {
        return is_goal;
    }

    public Boolean isImportant() {
        return is_important;
    }

    public Long getOrder() {
        return order;
    }

    @Override
    public int compareTo(Object o) {
        Comment aux = (Comment) o;
        if (getOrder() != null && aux.getOrder() != null) {
            return getOrder().intValue() - aux.getOrder().intValue();
        } else if (getOrder() == null)
            return -1;
        else
            return 1;
    }
}
