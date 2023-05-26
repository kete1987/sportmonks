package es.com.kete1987.sportmonks.library.v3.model.stage;

import java.util.List;

public class StageAggregate {
    private Long id;
    private Long league_id;
    private Long season_id;
    private Long stage_id;
    private String name;
    private List<Long> fixture_ids;
    private String result;
    private String detail;
    private Long winner_participant_id;

    private StageAggregate() {

    }

    public Long getId() {
        return id;
    }

    public Long getLeagueId() {
        return league_id;
    }

    public Long getSeasonId() {
        return season_id;
    }

    public Long getStageId() {
        return stage_id;
    }

    public String getName() {
        return name;
    }

    public List<Long> getFixtureIds() {
        return fixture_ids;
    }

    public String getResult() {
        return result;
    }

    public String getDetail() {
        return detail;
    }

    public Long getWinnerParticipantId() {
        return winner_participant_id;
    }
}
