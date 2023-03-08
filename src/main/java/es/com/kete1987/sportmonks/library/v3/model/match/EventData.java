package es.com.kete1987.sportmonks.library.v3.model.match;

public class EventData {
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

    public EventData() {}

    public Long getId() {
        return id;
    }

    public Long getFixture_id() {
        return fixture_id;
    }

    public Long getPeriod_id() {
        return period_id;
    }

    public Long getParticipant_id() {
        return participant_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public String getSection() {
        return section;
    }

    public Long getPlayer_id() {
        return player_id;
    }

    public Long getRelated_player_id() {
        return related_player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public String getRelated_player_name() {
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

    public Long getExtra_minute() {
        return extra_minute;
    }

    public Boolean getInjured() {
        return injured;
    }

    public Boolean getOn_bench() {
        return on_bench;
    }

    public Long getCoach_id() {
        return coach_id;
    }

    public Long getSub_type_id() {
        return sub_type_id;
    }
}
