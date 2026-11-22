package es.com.kete1987.sportmonks.library.football.model.comments;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.player.Player;
import org.jetbrains.annotations.Nullable;


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

    /**
     * Total order: {@code order} ascending with a null order first (as it always was), then
     * {@code id} ascending with a null id last. Two comments with an id compare as 0 only when they
     * are equal, so a {@code TreeSet} does not drop distinct comments.
     */
    @Override
    public int compareTo(Comment other) {
        if (order == null || other.order == null) {
            int byNull = Boolean.compare(other.order == null, order == null);
            if (byNull != 0) {
                return byNull;
            }
        } else {
            int byOrder = Long.compare(order, other.order);
            if (byOrder != 0) {
                return byOrder;
            }
        }
        if (id == null || other.id == null) {
            return Boolean.compare(id == null, other.id == null);
        }
        return Long.compare(id, other.id);
    }

    /** Comments are equal when they have the same non-null id; a comment without id is only equal to itself. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
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
