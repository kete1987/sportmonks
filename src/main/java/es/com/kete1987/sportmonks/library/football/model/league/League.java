package es.com.kete1987.sportmonks.library.football.model.league;

import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.football.model.sport.Sport;
import es.com.kete1987.sportmonks.library.football.model.stage.Stage;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

public class League {
    private Long id;
    @SerializedName("sport_id")
    private Long sportId;
    @SerializedName("country_id")
    private Long countryId;
    private String name;
    private Boolean active;
    @SerializedName("short_code")
    private String shortCode;
    @SerializedName("image_path")
    private String imagePath;
    private String type;
    @SerializedName("sub_type")
    private String subType;
    @SerializedName(value = "last_played_at", alternate = {"last_playerd_at"})
    private String lastPlayedAt;
    private Long category;
    @SerializedName("has_jerseys")
    private Boolean hasJerseys;
    private List<SeasonData> seasons;
    private SeasonData currentseason;
    private Country country;
    private List<MatchDetail> latest;
    private List<MatchDetail> upcoming;
    private List<Stage> stages;
    private Sport sport;

    public League() {
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

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    /** @return when the league last had a match ({@code yyyy-MM-dd HH:mm:ss}), or null. */
    @Nullable
    public String getLastPlayedAt() {
        return lastPlayedAt;
    }

    /**
     * @return the same value as {@link #getLastPlayedAt()}
     * @deprecated misspelt name kept for compatibility; use {@link #getLastPlayedAt()}
     */
    @Deprecated
    public String getLastPlayerdAt() {
        return getLastPlayedAt();
    }

    /** @return the league category (1 is the top one), or null. */
    @Nullable
    public Long getCategory() {
        return category;
    }

    public Boolean getHasJerseys() {
        return hasJerseys;
    }

    public List<SeasonData> getSeasons() {
        return ModelCollections.unmodifiable(seasons);
    }

    /** @return the current season, or null if none is active or the include is absent. */
    @Nullable
    public SeasonData getCurrentseason() {
        return currentseason;
    }

    /** @return the country, or null if unavailable or the include is absent. */
    @Nullable
    public Country getCountry() {
        return country;
    }

    /** @return the latest fixtures, or null if the {@code latest} include is absent. */
    @Nullable
    public List<MatchDetail> getLatest() {
        return ModelCollections.unmodifiable(latest);
    }

    /** @return the upcoming fixtures, or null if the {@code upcoming} include is absent. */
    @Nullable
    public List<MatchDetail> getUpcoming() {
        return ModelCollections.unmodifiable(upcoming);
    }

    /** @return the stages, or null if the {@code stages} include is absent. */
    @Nullable
    public List<Stage> getStages() {
        return ModelCollections.unmodifiable(stages);
    }

    /** @return the sport, or null if the {@code sport} include is absent. */
    @Nullable
    public Sport getSport() {
        return sport;
    }
}
