package es.com.kete1987.sportmonks.library.v3.model.standings;

import es.com.kete1987.sportmonks.library.v3.model.participant.Participant;
import es.com.kete1987.sportmonks.library.v3.model.rounds.Round;
import es.com.kete1987.sportmonks.library.v3.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.v3.model.stage.Stage;

import java.util.List;

public class Standings {
    private Long id;
    private Long participant_id;
    private Long sport_id;
    private Long league_id;
    private Long season_id;
    private Long stage_id;
    private Long group_id;
    private Long round_id;
    private Long standing_rule_id;
    private Long position;
    private String result;
    private Long points;
    private SeasonData season;
    private StandingsRule rule;
    private List<StandingsDetail> details;
    private Participant participant;
    private List<StandingsForm> form;
    private Stage stage;
//    private Group group; //TODO
    private Round round;

    public Standings() {}

    public Long getId() {
        return id;
    }

    public Long getParticipant_id() {
        return participant_id;
    }

    public Long getSport_id() {
        return sport_id;
    }

    public Long getLeague_id() {
        return league_id;
    }

    public Long getSeason_id() {
        return season_id;
    }

    public Long getStage_id() {
        return stage_id;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public Long getRound_id() {
        return round_id;
    }

    public Long getStanding_rule_id() {
        return standing_rule_id;
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
        return details;
    }

    public Participant getParticipant() {
        return participant;
    }

    public List<StandingsForm> getForm() {
        return form;
    }

    public Stage getStage() {
        return stage;
    }

    public Round getRound() {
        return round;
    }

    //TODO Descomentar
//    public Group getGroup() {
//        return group;
//    }
}
