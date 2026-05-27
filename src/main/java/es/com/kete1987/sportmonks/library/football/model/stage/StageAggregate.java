package es.com.kete1987.sportmonks.library.football.model.stage;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;

public class StageAggregate {
    private Long id;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("stage_id")
    private Long stageId;
    private String name;
    @SerializedName("fixture_ids")
    private List<Long> fixtureIds;
    private String result;
    private String detail;
    @SerializedName("winner_participant_id")
    private Long winnerParticipantId;

    private StageAggregate() {

    }

    public Long getId() {
        return id;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Long getStageId() {
        return stageId;
    }

    public String getName() {
        return name;
    }

    public List<Long> getFixtureIds() {
        return fixtureIds == null ? null : Collections.unmodifiableList(fixtureIds);
    }

    public String getResult() {
        return result;
    }

    public String getDetail() {
        return detail;
    }

    public Long getWinnerParticipantId() {
        return winnerParticipantId;
    }
}
