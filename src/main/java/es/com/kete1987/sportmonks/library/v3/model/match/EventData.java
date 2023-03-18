package es.com.kete1987.sportmonks.library.v3.model.match;

import es.com.kete1987.sportmonks.library.v3.model.player.Player;

public class EventData implements Comparable<Object> {
    private Long id;
    private Long fixture_id;
    private Long period_id;
    private Long participant_id;
    private Long type_id;
    private String section;
    private Long player_id;
    private Long related_player_id;
    private String player_name;
    private String related_player_name;
    private String result;
    private String info;
    private String addition;
    private Long minute;
    private Long extra_minute;
    private Boolean injured;
    private Boolean on_bench;
    private Long coach_id;
    private Long sub_type_id;
    private Player player;

    public EventData() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getPeriodId() {
        return period_id;
    }

    public Long getParticipantId() {
        return participant_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public String getSection() {
        return section;
    }

    public Long getPlayerId() {
        return player_id;
    }

    public Long getRelatedPlayerId() {
        return related_player_id;
    }

    public String getPlayerName() {
        return player_name;
    }

    public String getRelatedPlayerName() {
        return related_player_name;
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
        return extra_minute;
    }

    public Boolean getInjured() {
        return injured;
    }

    public Boolean getOnBench() {
        return on_bench;
    }

    public Long getCoachId() {
        return coach_id;
    }

    public Long getSubTypeId() {
        return sub_type_id;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean equals(Object o) {
        EventData ed = (EventData) o;
        return (ed.getId() != null ? ed.getId().intValue() : 0) == (getId() != null ? getId().intValue() : 0);
    }

    public int compareTo(Object o) {
        EventData ed = (EventData) o;
        return (getId() != null ? getId().intValue() : 0) - (ed.getId() != null ? ed.getId().intValue() : 0);
    }
}
