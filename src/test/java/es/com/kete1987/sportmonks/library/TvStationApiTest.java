package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.tvstation.TvStation;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TvStationApiTest extends BaseApiTest {

    @Test
    void getAllTvStations_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("tv_stations.json");

        api.getAllTvStations();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("tv-stations"));
        assertFalse(request.getPath().contains("tv-stations/"));
    }

    @Test
    void getAllTvStations_returnsParsedList() throws IOException, SportMonksException {
        enqueue("tv_stations.json");

        List<TvStation> stations = api.getAllTvStations();

        assertEquals(2, stations.size());
        assertEquals(7001L, stations.get(0).getId());
        assertEquals("Sky Sports", stations.get(0).getName());
        assertEquals(462L, stations.get(0).getCountryId());
    }

    @Test
    void getTvStationById_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("tv_station_detail.json");

        api.getTvStationById(7001L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("tv-stations/7001"));
    }

    @Test
    void getTvStationById_returnsParsed() throws IOException, SportMonksException {
        enqueue("tv_station_detail.json");

        TvStation station = api.getTvStationById(7001L);

        assertNotNull(station);
        assertEquals(7001L, station.getId());
        assertEquals("Sky Sports", station.getName());
        assertEquals("https://www.skysports.com", station.getUrl());
    }

    @Test
    void getTvStationsByFixture_usesCorrectPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("tv_stations.json");

        api.getTvStationsByFixture(1001L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("tv-stations/fixtures/1001"));
    }

    @Test
    void getTvStationsByFixture_returnsParsedList() throws IOException, SportMonksException {
        enqueue("tv_stations.json");

        List<TvStation> stations = api.getTvStationsByFixture(1001L);

        assertFalse(stations.isEmpty());
    }
}
