package es.com.kete1987.sportmonks.library.v3.model.topscorers;

import es.com.kete1987.sportmonks.library.v3.model.player.Player;
import es.com.kete1987.sportmonks.library.v3.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.v3.model.stage.Stage;
import es.com.kete1987.sportmonks.library.v3.model.team.Team;

public class TopScoresPlayer {
    private Long id;
    private Long season_id;
    private Long player_id;
    private Long type_id;
    private Long position;
    private Long total;
    private Long participant_id;
    private SeasonData season;
    private Stage stage;
    private Player player;
    private Team participant;
    private TopScoresType type;

    public TopScoresPlayer() {
    }

    public Long getId() {
        return id;
    }

    public Long getSeasonId() {
        return season_id;
    }

    public Long getPlayerId() {
        return player_id;
    }

    public Long getTypeId() {
        return type_id;
    }

    public Long getPosition() {
        return position;
    }

    public Long getTotal() {
        return total;
    }

    public Long getParticipantId() {
        return participant_id;
    }

    public SeasonData getSeason() {
        return season;
    }

    public Stage getStage() {
        return stage;
    }

    public Player getPlayer() {
        return player;
    }

    public TopScoresType getType() {
        return type;
    }

    public Team getParticipant() {
        return participant;
    }
}
