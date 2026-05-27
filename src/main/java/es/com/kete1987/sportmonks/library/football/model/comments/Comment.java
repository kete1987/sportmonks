package es.com.kete1987.sportmonks.library.football.model.comments;

import com.google.gson.annotations.SerializedName;

public class Comment implements Comparable<Object> {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    private String comment;
    private Long minute;
    @SerializedName("extra_minute")
    private Long extraMinute;
    @SerializedName("is_goal")
    private Boolean isGoal;
    @SerializedName("is_important")
    private Boolean isImportant;
    private Long order;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public String getComment() {
        return comment;
    }

    public Long getMinute() {
        return minute;
    }

    public Long getExtraMinute() {
        return extraMinute;
    }

    public Boolean isGoal() {
        return isGoal;
    }

    public Boolean isImportant() {
        return isImportant;
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
