package es.com.kete1987.sportmonks.library.football.model.comments;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Comment implements Comparable<Comment> {
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
    private MatchDetail fixture;
    private Player player;
    private Player relatedplayer;

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
    public int compareTo(Comment other) {
        if (getOrder() != null && other.getOrder() != null) {
            return Long.compare(getOrder(), other.getOrder());
        } else if (getOrder() == null) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        return Objects.equals(id, ((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /** @return the fixture, or null if the {@code fixture} include is absent. */
    @Nullable
    public MatchDetail getFixture() {
        return fixture;
    }

    /** @return the player the comment is about, or null if there is none or the include is absent. */
    @Nullable
    public Player getPlayer() {
        return player;
    }

    /** @return the second player involved (for example the one replaced), or null. */
    @Nullable
    public Player getRelatedPlayer() {
        return relatedplayer;
    }
}
