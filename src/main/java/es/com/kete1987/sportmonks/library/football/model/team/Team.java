package es.com.kete1987.sportmonks.library.football.model.team;

import es.com.kete1987.sportmonks.library.football.model.match.Match;
import es.com.kete1987.sportmonks.library.football.model.player.Sidelined;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class Team {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    @SerializedName("venue_id")
    private Long venueId;
    private String gender;
    private String name;
    @SerializedName("short_code")
    private String shortCode;
    @SerializedName("image_path")
    private String imagePath;
    private Long founded;
    private String type;
    private Boolean placeholder;
    @SerializedName("last_played_at")
    private String lastPlayedAt;
    private Venue venue;
    private List<TeamPlayer> players;
    private List<Match> latest;
    private List<Match> upcoming;
    private List<SeasonData> seasons;
    private List<SeasonData> activeseasons;
    private List<Sidelined> sidelined;
    private List<Sidelined> sidelinedhistory;

    private TeamMeta meta;

    public Team() {
    }

    public Long getId() {
        return id;
    }

    public Long getSportId() {
        return sportId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public Long getVenueId() {
        return venueId;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getFounded() {
        return founded;
    }

    public String getLastPlayedAt() {
        return lastPlayedAt;
    }

    public String getGender() {
        return gender;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getType() {
        return type;
    }

    public Boolean getPlaceholder() {
        return placeholder;
    }

    public TeamMeta getMeta() {
        return meta;
    }

    public Venue getVenue() {
        return venue;
    }

    public List<TeamPlayer> getPlayers() {
        return ModelCollections.unmodifiable(players);
    }

    public List<Match> getLatest() {
        return ModelCollections.unmodifiable(latest);
    }

    public List<Match> getUpcoming() {
        return ModelCollections.unmodifiable(upcoming);
    }

    public List<SeasonData> getSeasons() {
        return ModelCollections.unmodifiable(seasons);
    }

    public List<SeasonData> getActiveSeasons() {
        return ModelCollections.unmodifiable(activeseasons);
    }

    public List<Sidelined> getSidelined() {
        return ModelCollections.unmodifiable(sidelined);
    }

    public List<Sidelined> getSidelinedhistory() {
        return ModelCollections.unmodifiable(sidelinedhistory);
    }
}
