package es.com.kete1987.sportmonks.library.football.model.match;

import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.util.EventType;
import com.google.gson.annotations.SerializedName;

public class EventData implements Comparable<EventData> {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("period_id")
    private Long periodId;
    @SerializedName("participant_id")
    private Long participantId;
    @SerializedName("type_id")
    private Long typeId;
    private String section;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("related_player_id")
    private Long relatedPlayerId;
    @SerializedName("player_name")
    private String playerName;
    @SerializedName("related_player_name")
    private String relatedPlayerName;
    private String result;
    private String info;
    private String addition;
    private Long minute;
    @SerializedName("extra_minute")
    private Long extraMinute;
    private Boolean injured;
    @SerializedName("on_bench")
    private Boolean onBench;
    @SerializedName("coach_id")
    private Long coachId;
    @SerializedName("sub_type_id")
    private Long subTypeId;
    @SerializedName("sort_order")
    private Long sortOrder;
    private Boolean rescinded;
    @SerializedName("detailed_period_id")
    private Long detailedPeriodId;
    private Player player;

    public EventData() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public String getSection() {
        return section;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getRelatedPlayerId() {
        return relatedPlayerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getRelatedPlayerName() {
        return relatedPlayerName;
    }

    public String getResult() {
        return result;
    }

    public String getInfo() {
        return info;
    }

    public String getAddition() {
        return addition;
    }

    public Long getMinute() {
        return minute;
    }

    public Long getExtraMinute() {
        return extraMinute;
    }

    public Boolean getInjured() {
        return injured;
    }

    public Boolean getOnBench() {
        return onBench;
    }

    public Long getCoachId() {
        return coachId;
    }

    public Long getSubTypeId() {
        return subTypeId;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public Boolean getRescinded() {
        return rescinded;
    }

    public Long getDetailedPeriodId() {
        return detailedPeriodId;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * @return {@code true} if this event belongs to a penalty shootout
     * ({@link EventType#PENALTY_SHOOTOUT_GOAL} scored / {@link EventType#PENALTY_SHOOTOUT_MISS} missed).
     * Their {@code minute} is the penalty number (1..N), not a match minute.
     */
    public boolean isShootoutEvent() {
        return typeId != null
                && (typeId.intValue() == EventType.PENALTY_SHOOTOUT_GOAL
                || typeId.intValue() == EventType.PENALTY_SHOOTOUT_MISS);
    }

    /** Events are equal when they have the same non-null id; an event without id is only equal to itself. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventData)) return false;
        return id != null && id.equals(((EventData) o).id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

    /**
     * Total order, with penalty shootout events (scored 23 / missed 22) last because their
     * {@code minute} is the penalty number (1..N), not a match minute. Within each group:
     * {@code minute} ascending (null last), {@code extra_minute} ascending (null counts as 0, so
     * stoppage-time events follow the ones at the plain minute), then {@code id} ascending (null
     * last). Two events with an id compare as 0 only when they are equal.
     */
    @Override
    public int compareTo(EventData other) {
        boolean thisShootout = isShootoutEvent();
        boolean otherShootout = other.isShootoutEvent();
        if (thisShootout != otherShootout) {
            return thisShootout ? 1 : -1;
        }
        int byMinute = compareNullsLast(minute, other.minute);
        if (byMinute != 0) {
            return byMinute;
        }
        int byExtra = Long.compare(extraMinute == null ? 0L : extraMinute, other.extraMinute == null ? 0L : other.extraMinute);
        if (byExtra != 0) {
            return byExtra;
        }
        return compareNullsLast(id, other.id);
    }

    private static int compareNullsLast(Long a, Long b) {
        if (a == null || b == null) {
            return Boolean.compare(a == null, b == null);
        }
        return Long.compare(a, b);
    }
}
