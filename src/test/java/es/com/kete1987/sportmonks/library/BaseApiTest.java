package es.com.kete1987.sportmonks.library;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public abstract class BaseApiTest {

    protected MockWebServer server;
    protected SportMonksAPI api;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();

        String base = server.url("/").toString();
        OkHttpClient client = new OkHttpClient();
        api = new SportMonksAPI(client, base, base, base, base);
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    protected void enqueue(String fixture) throws IOException {
        enqueue(fixture, 200);
    }

    protected void enqueue(String fixture, int code) throws IOException {
        String body = load(fixture);
        server.enqueue(new MockResponse()
                .setResponseCode(code)
                .setBody(body)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("X-RateLimit-Limit", "3000")
                .addHeader("X-RateLimit-Remaining", "2999"));
    }

    private String load(String name) throws IOException {
        String path = "fixtures/" + name;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IOException("Test fixture not found: " + path);
            return new String(readAll(is), StandardCharsets.UTF_8);
        }
    }

    private byte[] readAll(InputStream is) throws IOException {
        byte[] buffer = new byte[4096];
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        int n;
        while ((n = is.read(buffer)) != -1) out.write(buffer, 0, n);
        return out.toByteArray();
    }
}
