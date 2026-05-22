package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.city.City;
import es.com.kete1987.sportmonks.library.core.model.continent.Continent;
import es.com.kete1987.sportmonks.library.core.model.country.Country;
import es.com.kete1987.sportmonks.library.core.model.region.Region;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoreApiTest extends BaseApiTest {

    // --- Continents ---

    @Test
    void getAllContinents_returnsParsedList() throws IOException, SportMonksException {
        enqueue("continents.json");

        List<Continent> continents = api.getAllContinents();

        assertEquals(2, continents.size());
        assertEquals(1L, continents.get(0).getId());
        assertEquals("Europe", continents.get(0).getName());
        assertEquals("EU", continents.get(0).getCode());
        assertEquals(2L, continents.get(1).getId());
        assertEquals("South America", continents.get(1).getName());
    }

    @Test
    void getAllContinents_urlUsesCoreBase() throws IOException, SportMonksException, InterruptedException {
        enqueue("continents.json");

        api.getAllContinents();

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("continents"),
                "Expected continents path, got: " + request.getPath());
    }

    @Test
    void getContinentById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("continent_detail.json");

        Continent continent = api.getContinentById(1L);

        assertNotNull(continent);
        assertEquals(1L, continent.getId());
        assertEquals("Europe", continent.getName());
        assertEquals("EU", continent.getCode());
    }

    @Test
    void getContinentById_urlContainsId() throws IOException, SportMonksException, InterruptedException {
        enqueue("continent_detail.json");

        api.getContinentById(1L);

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("continents/1"),
                "Expected path with continent id, got: " + request.getPath());
    }

    // --- Countries ---

    @Test
    void getAllCountries_returnsParsedList() throws IOException, SportMonksException {
        enqueue("countries.json");

        List<Country> countries = api.getAllCountries();

        assertEquals(2, countries.size());
        assertEquals(1L, countries.get(0).getId());
        assertEquals("Germany", countries.get(0).getName());
        assertEquals("GER", countries.get(0).getFifaName());
        assertEquals("DE", countries.get(0).getIso2());
        assertEquals(1L, countries.get(0).getContinentId());
        assertEquals(2L, countries.get(1).getId());
        assertEquals("France", countries.get(1).getName());
    }

    @Test
    void getCountryById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("country_detail.json");

        Country country = api.getCountryById(1L);

        assertNotNull(country);
        assertEquals(1L, country.getId());
        assertEquals("Germany", country.getName());
        assertEquals("Federal Republic of Germany", country.getOfficialName());
        assertEquals("DEU", country.getIso3());
    }

    @Test
    void searchCountries_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("countries.json");

        api.searchCountries("Germany");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("countries/search/Germany"),
                "Expected search path, got: " + request.getPath());
    }

    @Test
    void searchCountries_returnsParsedList() throws IOException, SportMonksException {
        enqueue("countries.json");

        List<Country> countries = api.searchCountries("Germany");

        assertEquals(2, countries.size());
        assertEquals("Germany", countries.get(0).getName());
    }

    // --- Regions ---

    @Test
    void getAllRegions_returnsParsedList() throws IOException, SportMonksException {
        enqueue("regions.json");

        List<Region> regions = api.getAllRegions();

        assertEquals(2, regions.size());
        assertEquals(1L, regions.get(0).getId());
        assertEquals("Bavaria", regions.get(0).getName());
        assertEquals(1L, regions.get(0).getCountryId());
        assertEquals(2L, regions.get(1).getId());
        assertEquals("Berlin", regions.get(1).getName());
    }

    @Test
    void getRegionById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("region_detail.json");

        Region region = api.getRegionById(1L);

        assertNotNull(region);
        assertEquals(1L, region.getId());
        assertEquals("Bavaria", region.getName());
        assertEquals(1L, region.getCountryId());
    }

    @Test
    void searchRegions_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("regions.json");

        api.searchRegions("Bavaria");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("regions/search/Bavaria"),
                "Expected search path, got: " + request.getPath());
    }

    @Test
    void searchRegions_returnsParsedList() throws IOException, SportMonksException {
        enqueue("regions.json");

        List<Region> regions = api.searchRegions("Bavaria");

        assertEquals(2, regions.size());
    }

    // --- Cities ---

    @Test
    void getAllCities_returnsParsedList() throws IOException, SportMonksException {
        enqueue("cities.json");

        List<City> cities = api.getAllCities();

        assertEquals(2, cities.size());
        assertEquals(1L, cities.get(0).getId());
        assertEquals("Munich", cities.get(0).getName());
        assertEquals(1L, cities.get(0).getRegionId());
        assertEquals(1L, cities.get(0).getCountryId());
        assertEquals(2L, cities.get(1).getId());
        assertEquals("Nuremberg", cities.get(1).getName());
    }

    @Test
    void getCityById_returnsSingleParsed() throws IOException, SportMonksException {
        enqueue("city_detail.json");

        City city = api.getCityById(1L);

        assertNotNull(city);
        assertEquals(1L, city.getId());
        assertEquals("Munich", city.getName());
        assertEquals("48.1374", city.getLatitude());
        assertEquals("11.5755", city.getLongitude());
    }

    @Test
    void searchCities_usesSearchPath() throws IOException, SportMonksException, InterruptedException {
        enqueue("cities.json");

        api.searchCities("Munich");

        RecordedRequest request = server.takeRequest();
        assertTrue(request.getPath().contains("cities/search/Munich"),
                "Expected search path, got: " + request.getPath());
    }

    @Test
    void searchCities_returnsParsedList() throws IOException, SportMonksException {
        enqueue("cities.json");

        List<City> cities = api.searchCities("Munich");

        assertEquals(2, cities.size());
    }
}
