package es.com.kete1987.sportmonks.library.football.model.coach;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import es.com.kete1987.sportmonks.library.football.model.league.Country;
import es.com.kete1987.sportmonks.library.football.model.seasonstats.ParticipantSeasonStats;
import es.com.kete1987.sportmonks.library.football.model.team.TeamCoach;

import java.util.List;
import org.jetbrains.annotations.Nullable;

public class Coach {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    @SerializedName("nationality_id")
    private Long nationalityId;
    private String name;
    private String firstname;
    private String lastname;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("image_path")
    private String imagePath;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    private String gender;
    @Nullable
    @SerializedName("common_name")
    private String commonName;
    @Nullable
    private CoachMeta meta;
    @SerializedName("player_id")
    private Long playerId;
    @SerializedName("city_id")
    private Long cityId;
    private Long height;
    private Long weight;
    private Country country;
    private Country nationality;
    private List<TeamCoach> teams;
    private List<ParticipantSeasonStats> statistics;

    public Coach() {
    }

    /** @return the short common name (e.g. {@code "H. Flick"}), or null when unavailable. */
    @Nullable
    public String getCommonName() {
        return commonName;
    }

    /**
     * @return the fixture pivot (team the coach led), present when the coach comes from the
     *         fixture {@code coaches} include; null otherwise.
     */
    @Nullable
    public CoachMeta getMeta() {
        return meta;
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

    public Long getNationalityId() {
        return nationalityId;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    /** @return the id of the coach as a player (the same person's player profile), or null. */
    @Nullable
    public Long getPlayerId() {
        return playerId;
    }

    @Nullable
    public Long getCityId() {
        return cityId;
    }

    /** @return the height in centimetres, or null. */
    @Nullable
    public Long getHeight() {
        return height;
    }

    /** @return the weight in kilograms, or null. */
    @Nullable
    public Long getWeight() {
        return weight;
    }

    /** @return the country, or null if the {@code country} include is absent. */
    @Nullable
    public Country getCountry() {
        return country;
    }

    /** @return the nationality, or null if the {@code nationality} include is absent. */
    @Nullable
    public Country getNationality() {
        return nationality;
    }

    /** @return the teams the coach has led, or null if the {@code teams} include is absent. */
    @Nullable
    public List<TeamCoach> getTeams() {
        return ModelCollections.unmodifiable(teams);
    }

    /** @return the seasons that have statistics, or null if the {@code statistics} include is absent. */
    @Nullable
    public List<ParticipantSeasonStats> getStatistics() {
        return ModelCollections.unmodifiable(statistics);
    }
}
