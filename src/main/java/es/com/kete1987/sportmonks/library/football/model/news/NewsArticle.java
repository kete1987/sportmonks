package es.com.kete1987.sportmonks.library.football.model.news;

import com.google.gson.annotations.SerializedName;

public class NewsArticle {
    private Long id;
    @SerializedName("fixture_id")
    private Long fixtureId;
    @SerializedName("season_id")
    private Long seasonId;
    private String author;
    private String title;
    private String body;
    @SerializedName("image_path")
    private String imagePath;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("published_at")
    private String publishedAt;
    private String type;

    public NewsArticle() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getType() {
        return type;
    }
}
