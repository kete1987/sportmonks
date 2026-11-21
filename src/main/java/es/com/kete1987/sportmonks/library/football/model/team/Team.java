package es.com.kete1987.sportmonks.library.football.model.team;

import es.com.kete1987.sportmonks.library.football.model.league.Country;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.seasonstats.ParticipantSeasonStats;
import es.com.kete1987.sportmonks.library.football.model.sport.Sport;
import es.com.kete1987.sportmonks.library.football.model.trophy.Trophy;
import org.jetbrains.annotations.Nullable;
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
    private List<MatchDetail> latest;
    private List<MatchDetail> upcoming;
    private List<SeasonData> seasons;
    private List<SeasonData> activeseasons;
    private List<Sidelined> sidelined;
    private List<Sidelined> sidelinedhistory;
    private List<TeamCoach> coaches;
    private Country country;
    private List<TeamRankingEntry> rankings;
    private List<Team> rivals;
    private List<TeamSocial> socials;
    private Sport sport;
    private List<ParticipantSeasonStats> statistics;
    private List<Trophy> trophies;

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

    public List<MatchDetail> getLatest() {
        return ModelCollections.unmodifiable(latest);
    }

    public List<MatchDetail> getUpcoming() {
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

    /** @return the coaching spells, or null if the {@code coaches} include is absent. */
    @Nullable
    public List<TeamCoach> getCoaches() {
        return ModelCollections.unmodifiable(coaches);
    }

    /** @return the country, or null if the {@code country} include is absent. */
    @Nullable
    public Country getCountry() {
        return country;
    }

    /** @return the rankings, or null if the {@code rankings} include is absent. */
    @Nullable
    public List<TeamRankingEntry> getRankings() {
        return ModelCollections.unmodifiable(rankings);
    }

    /** @return the rival teams, or null if the {@code rivals} include is absent. */
    @Nullable
    public List<Team> getRivals() {
        return ModelCollections.unmodifiable(rivals);
    }

    /** @return the social accounts, or null if the {@code socials} include is absent. */
    @Nullable
    public List<TeamSocial> getSocials() {
        return ModelCollections.unmodifiable(socials);
    }

    /** @return the sport, or null if the {@code sport} include is absent. */
    @Nullable
    public Sport getSport() {
        return sport;
    }

    /** @return the seasons that have statistics, or null if the {@code statistics} include is absent. */
    @Nullable
    public List<ParticipantSeasonStats> getStatistics() {
        return ModelCollections.unmodifiable(statistics);
    }

    /** @return the trophies, or null if the {@code trophies} include is absent. */
    @Nullable
    public List<Trophy> getTrophies() {
        return ModelCollections.unmodifiable(trophies);
    }
}
