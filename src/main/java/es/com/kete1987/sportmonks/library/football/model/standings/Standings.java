package es.com.kete1987.sportmonks.library.football.model.standings;

import es.com.kete1987.sportmonks.library.football.model.rounds.Round;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.model.team.Team;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;

public class Standings {
    private Long id;
    @SerializedName("participant_id")
    private Long participantId;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("league_id")
    private Long leagueId;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("stage_id")
    private Long stageId;
    @SerializedName("group_id")
    private Long groupId;
    @SerializedName("round_id")
    private Long roundId;
    @SerializedName("standing_rule_id")
    private Long standingRuleId;
    private Long position;
    private String result;
    private Long points;
    private SeasonData season;
    private StandingsRule rule;
    private List<StandingsDetail> details;
    private Team participant;
    private List<StandingsForm> form;
    private Stage stage;
    private StandingsGroup group;
    private Round round;

    public Standings() {
    }

    public Long getId() {
        return id;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public Long getSportId() {
        return sportId;
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

    public Long getGroupId() {
        return groupId;
    }

    public Long getRoundId() {
        return roundId;
    }

    public Long getStandingRuleId() {
        return standingRuleId;
    }

    public Long getPosition() {
        return position;
    }

    public String getResult() {
        return result;
    }

    public Long getPoints() {
        return points;
    }

    public SeasonData getSeason() {
        return season;
    }

    public StandingsRule getRule() {
        return rule;
    }

    public List<StandingsDetail> getDetails() {
        return details == null ? null : Collections.unmodifiableList(details);
    }

    public Team getParticipant() {
        return participant;
    }

    public List<StandingsForm> getForm() {
        return form == null ? null : Collections.unmodifiableList(form);
    }

    public Stage getStage() {
        return stage;
    }

    public Round getRound() {
        return round;
    }

    public StandingsGroup getGroup() {
        return group;
    }

    public StandingsDetail getDetails(int type) {
        for (StandingsDetail sd : getDetails()) {
            if (sd.getTypeId().intValue() == type)
                return sd;
        }
        return null;
    }
}
