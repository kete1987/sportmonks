package es.com.kete1987.sportmonks.library.football.model.statistic;

public class Statistic {
    private Long id;
    private Long type_id;
    private Long participant_id;
    private String participant_type;
    private Object value;
    private Long season_id;
    private Long stage_id;

    public Statistic() {
    }

    public Long getId() {
        return id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public Long getParticipantId() {
        return participant_id;
    }

    public String getParticipantType() {
        return participant_type;
    }

    public Object getValue() {
        return value;
    }

    public Long getSeasonId() {
        return season_id;
    }

    public Long getStageId() {
        return stage_id;
    }
}
