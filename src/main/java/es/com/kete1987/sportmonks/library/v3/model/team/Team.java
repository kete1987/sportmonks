package es.com.kete1987.sportmonks.library.v3.model.team;

import es.com.kete1987.sportmonks.library.v3.model.match.Match;
import es.com.kete1987.sportmonks.library.v3.model.player.Sidelined;
import es.com.kete1987.sportmonks.library.v3.model.season.SeasonData;
import es.com.kete1987.sportmonks.library.v3.model.venue.Venue;

import java.util.List;

public class Team {
    private Long id;
    private Long sport_id;
    private Long country_id;
    private Long venue_id;
    private String gender;
    private String name;
    private String short_code;
    private String image_path;
    private Long founded;
    private String type;
    private Boolean placeholder;
    private String last_played_at;
    private Venue venue;
    private List<TeamPlayer> players;
    private List<Match> latest;
    private List<Match> upcoming;
    private List<SeasonData> seasons;
    private List<SeasonData> activeseasons;
    private List<Sidelined> sidelined;
    private List<Sidelined> sidelinedhistory;

    private TeamMeta meta;

    public Team() {}

    public Long getId() {
        return id;
    }

    public Long getSport_id() {
        return sport_id;
    }

    public Long getCountry_id() {
        return country_id;
    }

    public Long getVenue_id() {
        return venue_id;
    }

    public String getName() {
        return name;
    }

    public String getImage_path() {
        return image_path;
    }

    public Long getFounded() {
        return founded;
    }

    public String getLast_played_at() {
        return last_played_at;
    }

    public String getGender() {
        return gender;
    }

    public String getShort_code() {
        return short_code;
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
        return players;
    }

    public List<Match> getLatest() {
        return latest;
    }

    public List<Match> getUpcoming() {
        return upcoming;
    }

    public List<SeasonData> getSeasons() {
        return seasons;
    }

    public List<SeasonData> getActiveseasons() {
        return activeseasons;
    }

    public List<Sidelined> getSidelined() {
        return sidelined;
    }

    public List<Sidelined> getSidelinedhistory() {
        return sidelinedhistory;
    }
}
