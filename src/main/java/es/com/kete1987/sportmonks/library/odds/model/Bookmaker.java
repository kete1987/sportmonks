package es.com.kete1987.sportmonks.library.odds.model;

public class Bookmaker {
    private Long id;
    private Long legacy_id;
    private String name;
    private String display_name;
    private Boolean has_streaming;
    private Boolean is_premium;
    private String image_path;

    public Bookmaker() {
    }

    public Long getId() {
        return id;
    }

    public Long getLegacyId() {
        return legacy_id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return display_name;
    }

    public Boolean getHasStreaming() {
        return has_streaming;
    }

    public Boolean getIsPremium() {
        return is_premium;
    }

    public String getImagePath() {
        return image_path;
    }
}
