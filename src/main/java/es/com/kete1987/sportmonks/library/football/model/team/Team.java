package es.com.kete1987.sportmonks.library.football.model.team;

import es.com.kete1987.sportmonks.library.football.model.match.Match;
import es.com.kete1987.sportmonks.library.football.model.player.Sidelined;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.venue.Venue;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;

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
        return players == null ? null : Collections.unmodifiableList(players);
    }

    public List<Match> getLatest() {
        return latest == null ? null : Collections.unmodifiableList(latest);
    }

    public List<Match> getUpcoming() {
        return upcoming == null ? null : Collections.unmodifiableList(upcoming);
    }

    public List<SeasonData> getSeasons() {
        return seasons == null ? null : Collections.unmodifiableList(seasons);
    }

    public List<SeasonData> getActiveSeasons() {
        return activeseasons == null ? null : Collections.unmodifiableList(activeseasons);
    }

    public List<Sidelined> getSidelined() {
        return sidelined == null ? null : Collections.unmodifiableList(sidelined);
    }

    public List<Sidelined> getSidelinedhistory() {
        return sidelinedhistory == null ? null : Collections.unmodifiableList(sidelinedhistory);
    }
}
