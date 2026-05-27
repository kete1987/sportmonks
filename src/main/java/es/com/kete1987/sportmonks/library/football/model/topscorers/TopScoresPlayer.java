package es.com.kete1987.sportmonks.library.football.model.topscorers;

import es.com.kete1987.sportmonks.library.football.model.player.Player;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;
import es.com.kete1987.sportmonks.library.football.model.team.Team;
import com.google.gson.annotations.SerializedName;

public class TopScoresPlayer {
    private Long id;
    @SerializedName("season_id")
    private Long seasonId;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("type_id")
    private Long typeId;
    private Long position;
    private Long total;
    @SerializedName("participant_id")
    private Long participantId;
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
        return seasonId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getPosition() {
        return position;
    }

    public Long getTotal() {
        return total;
    }

    public Long getParticipantId() {
        return participantId;
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
