package es.com.kete1987.sportmonks.library.football.model.team;

import com.google.gson.annotations.SerializedName;

/** A social media account of a team, as returned by the {@code socials} include. */
public class TeamSocial {
    private Long id;
    @SerializedName("social_id")
    private Long socialId;
    @SerializedName("social_channel_id")
    private Long socialChannelId;
    private String value;

    public TeamSocial() {
    }

    public Long getId() {
        return id;
    }

    public Long getSocialId() {
        return socialId;
    }

    /** @return the id of the channel type (Twitter, Instagram, ...), see {@code core/types}. */
    public Long getSocialChannelId() {
        return socialChannelId;
    }

    /** @return the handle or URL on that channel. */
    public String getValue() {
        return value;
    }
}