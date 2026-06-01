package es.com.kete1987.sportmonks.library.football.model.match;

import es.com.kete1987.sportmonks.library.football.model.player.Player;
import com.google.gson.annotations.SerializedName;

public class EventData implements Comparable<Object> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventData)) return false;
        EventData ed = (EventData) o;
        return (ed.getId() != null ? ed.getId().intValue() : 0) == (getId() != null ? getId().intValue() : 0);
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().intValue() : 0;
    }

    @Override
    public int compareTo(Object o) {
        EventData ed = (EventData) o;
        if (getMinute() != null && ed.getMinute() != null) {
            if (getMinute().intValue() == ed.getMinute().intValue()) {
                if (getExtraMinute() != null && ed.getExtraMinute() != null) {
                    return getExtraMinute().intValue() - ed.getExtraMinute().intValue();
                }
            } else
                return getMinute().intValue() - ed.getMinute().intValue();
        }
        return (getId() != null ? getId().intValue() : 0) - (ed.getId() != null ? ed.getId().intValue() : 0);
    }
}
