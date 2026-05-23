package es.com.kete1987.sportmonks.library.football.model.news;

public class NewsArticle {
    private Long id;
    private Long fixture_id;
    private Long season_id;
    private String author;
    private String title;
    private String body;
    private String image_path;
    private String created_at;
    private String published_at;
    private String type;

    public NewsArticle() {
    }

    public Long getId() {
        return id;
    }

    public Long getFixtureId() {
        return fixture_id;
    }

    public Long getSeasonId() {
        return season_id;
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
        return image_path;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getPublishedAt() {
        return published_at;
    }

    public String getType() {
        return type;
    }
}
