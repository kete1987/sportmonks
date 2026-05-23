package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.news.NewsArticle;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsApiTest extends BaseApiTest {

    @Test
    void getAllPreMatchNews_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("news_articles.json");

        api.getAllPreMatchNews();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("news/pre-match"));
        assertFalse(request.getPath().contains("seasons"));
        assertFalse(request.getPath().contains("upcoming"));
    }

    @Test
    void getAllPreMatchNews_returnsParsedList() throws IOException, SportMonksException {
        enqueue("news_articles.json");

        List<NewsArticle> articles = api.getAllPreMatchNews();

        assertEquals(1, articles.size());
        assertEquals(4001L, articles.get(0).getId());
        assertEquals(100L, articles.get(0).getFixtureId());
        assertEquals(19686L, articles.get(0).getSeasonId());
        assertEquals("Preview: Team A vs Team B", articles.get(0).getTitle());
        assertEquals("pre-match", articles.get(0).getType());
    }

    @Test
    void getPreMatchNewsBySeason_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("news_articles.json");

        api.getPreMatchNewsBySeason(19686L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("news/pre-match/seasons/19686"));
    }

    @Test
    void getPreMatchNewsForUpcoming_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("news_articles.json");

        api.getPreMatchNewsForUpcoming();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("news/pre-match/upcoming"));
    }

    @Test
    void getAllPostMatchNews_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("news_articles.json");

        api.getAllPostMatchNews();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("news/post-match"));
        assertFalse(request.getPath().contains("seasons"));
    }

    @Test
    void getPostMatchNewsBySeason_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("news_articles.json");

        api.getPostMatchNewsBySeason(19686L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("news/post-match/seasons/19686"));
    }
}
