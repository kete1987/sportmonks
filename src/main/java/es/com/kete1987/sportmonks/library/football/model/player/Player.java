package es.com.kete1987.sportmonks.library.football.model.player;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.core.model.city.City;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;
import es.com.kete1987.sportmonks.library.football.model.league.Country;
import es.com.kete1987.sportmonks.library.football.model.match.FixtureMetadata;
import es.com.kete1987.sportmonks.library.football.model.seasonstats.ParticipantSeasonStats;
import es.com.kete1987.sportmonks.library.football.model.sport.Sport;
import es.com.kete1987.sportmonks.library.football.model.team.TeamPlayer;
import es.com.kete1987.sportmonks.library.football.model.transfer.Transfer;
import es.com.kete1987.sportmonks.library.football.model.trophy.Trophy;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Player {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    @SerializedName("nationality_id")
    private Long nationalityId;
    @SerializedName("city_id")
    private Long cityId;
    @SerializedName("position_id")
    private Long positionId;
    @SerializedName("detailed_position_id")
    private Long detailedPositionId;
    @SerializedName("type_id")
    private Long typeId;
    @SerializedName("common_name")
    private String commonName;
    private String firstname;
    private String lastname;
    private String name;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("image_path")
    private String imagePath;
    private Long height;
    private Long weight;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    private String gender;
    private City city;
    private Country country;
    private Country nationality;
    private Type position;
    private Type detailedposition;
    private List<TeamPlayer> teams;
    private List<Transfer> transfers;
    private List<Transfer> pendingtransfers;
    private List<ParticipantSeasonStats> statistics;
    private List<FixtureMetadata> metadata;
    private List<Trophy> trophies;
    private Sport sport;

    public Player() {
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

    public Long getCityId() {
        return cityId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public Long getDetailedPositionId() {
        return detailedPositionId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getHeight() {
        return height;
    }

    public Long getWeight() {
        return weight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    /** @return the birth city, or null if unknown or the {@code city} include is absent. */
    @Nullable
    public City getCity() {
        return city;
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

    /** @return the position, or null if the {@code position} include is absent. */
    @Nullable
    public Type getPosition() {
        return position;
    }

    /** @return the detailed position, or null if the {@code detailedPosition} include is absent. */
    @Nullable
    public Type getDetailedPosition() {
        return detailedposition;
    }

    /** @return the team spells, or null if the {@code teams} include is absent. */
    @Nullable
    public List<TeamPlayer> getTeams() {
        return ModelCollections.unmodifiable(teams);
    }

    /** @return the completed transfers, or null if the {@code transfers} include is absent. */
    @Nullable
    public List<Transfer> getTransfers() {
        return ModelCollections.unmodifiable(transfers);
    }

    /** @return the pending transfers, or null if the {@code pendingTransfers} include is absent. */
    @Nullable
    public List<Transfer> getPendingTransfers() {
        return ModelCollections.unmodifiable(pendingtransfers);
    }

    /** @return the seasons that have statistics, or null if the {@code statistics} include is absent. */
    @Nullable
    public List<ParticipantSeasonStats> getStatistics() {
        return ModelCollections.unmodifiable(statistics);
    }

    /** @return the player metadata (preferred foot, ...), or null if the {@code metadata} include is absent. */
    @Nullable
    public List<FixtureMetadata> getMetadata() {
        return ModelCollections.unmodifiable(metadata);
    }

    /** @return the trophies, or null if the {@code trophies} include is absent. */
    @Nullable
    public List<Trophy> getTrophies() {
        return ModelCollections.unmodifiable(trophies);
    }

    /** @return the sport, or null if the {@code sport} include is absent. */
    @Nullable
    public Sport getSport() {
        return sport;
    }
}
