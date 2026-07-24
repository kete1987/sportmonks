package es.com.kete1987.sportmonks.library;

import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.core.model.city.CitiesResponse;
import es.com.kete1987.sportmonks.library.core.model.city.City;
import es.com.kete1987.sportmonks.library.core.model.city.CityResponse;
import es.com.kete1987.sportmonks.library.core.model.continent.Continent;
import es.com.kete1987.sportmonks.library.core.model.continent.ContinentResponse;
import es.com.kete1987.sportmonks.library.core.model.continent.ContinentsResponse;
import es.com.kete1987.sportmonks.library.core.model.country.CountriesResponse;
import es.com.kete1987.sportmonks.library.core.model.country.Country;
import es.com.kete1987.sportmonks.library.core.model.country.CountryResponse;
import es.com.kete1987.sportmonks.library.core.model.filter.FiltersResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyApi;
import es.com.kete1987.sportmonks.library.core.model.my.MyApiResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyEnrichmentsResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeague;
import es.com.kete1987.sportmonks.library.core.model.my.MyLeaguesResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyResource;
import es.com.kete1987.sportmonks.library.core.model.my.MyResourcesResponse;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsage;
import es.com.kete1987.sportmonks.library.core.model.my.MyUsagesResponse;
import es.com.kete1987.sportmonks.library.core.model.region.Region;
import es.com.kete1987.sportmonks.library.core.model.region.RegionResponse;
import es.com.kete1987.sportmonks.library.core.model.region.RegionsResponse;
import es.com.kete1987.sportmonks.library.core.model.timezone.TimezonesResponse;
import es.com.kete1987.sportmonks.library.core.model.type.Type;
import es.com.kete1987.sportmonks.library.core.model.type.TypeResponse;
import es.com.kete1987.sportmonks.library.core.model.type.TypesResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Core sub-API: continents, countries, regions, cities, types, timezones, filters,
 * and MySportmonks account data.
 *
 * <p>Can be used standalone ({@code new CoreApi("apiKey")}) or via the
 * {@link SportMonksAPI} facade ({@code api.getCore()}).
 */
public class CoreApi extends SportMonksApiBase {

    private final String coreBase;
    private final String myBase;

    public CoreApi(String apiToken) {
        this(apiToken, null);
    }

    public CoreApi(String apiToken, String locale) {
        this(apiToken, locale, null);
    }

    public CoreApi(String apiToken, String locale, String timezone) {
        this(buildHttpClient(apiToken), Constants.BASE_URL_CORE, Constants.BASE_URL_MY, locale, timezone, new RateLimitTracker());
    }

    CoreApi(OkHttpClient client, String coreBase, String myBase, String locale, String timezone, RateLimitTracker tracker) {
        super(client, locale, timezone, tracker);
        this.coreBase = coreBase;
        this.myBase = myBase;
    }

    private HttpUrl.Builder coreUrl(String path) {
        return timezoneUrl(localeUrl(HttpUrl.parse(coreBase + path).newBuilder()));
    }

    private HttpUrl.Builder myUrl(String path) {
        return timezoneUrl(localeUrl(HttpUrl.parse(myBase + path).newBuilder()));
    }

    // -------------------------------------------------------------------------
    // Continents
    // -------------------------------------------------------------------------

    public List<Continent> getAllContinents(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("continents"), includes).build();
        return fetchPaged(base, ContinentsResponse.class, ContinentsResponse::getData, ContinentsResponse::getPagination, 0);
    }

    public Continent getContinentById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("continents/" + id), includes).build();
        return gson().fromJson(execute(url), ContinentResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Countries
    // -------------------------------------------------------------------------

    public List<Country> getAllCountries(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("countries"), includes).build();
        return fetchPaged(base, CountriesResponse.class, CountriesResponse::getData, CountriesResponse::getPagination, 0);
    }

    public Country getCountryById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("countries/" + id), includes).build();
        return gson().fromJson(execute(url), CountryResponse.class).getData();
    }

    public List<Country> searchCountries(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("countries/search/" + name), includes).build();
        return fetchPaged(base, CountriesResponse.class, CountriesResponse::getData, CountriesResponse::getPagination, 0);
    }

    // -------------------------------------------------------------------------
    // Regions
    // -------------------------------------------------------------------------

    public List<Region> getAllRegions(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("regions"), includes).build();
        return fetchPaged(base, RegionsResponse.class, RegionsResponse::getData, RegionsResponse::getPagination, 0);
    }

    public Region getRegionById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("regions/" + id), includes).build();
        return gson().fromJson(execute(url), RegionResponse.class).getData();
    }

    public List<Region> searchRegions(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("regions/search/" + name), includes).build();
        return fetchPaged(base, RegionsResponse.class, RegionsResponse::getData, RegionsResponse::getPagination, 0);
    }

    // -------------------------------------------------------------------------
    // Cities
    // -------------------------------------------------------------------------

    public List<City> getAllCities(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("cities"), includes).build();
        return fetchPaged(base, CitiesResponse.class, CitiesResponse::getData, CitiesResponse::getPagination, 0);
    }

    public City getCityById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("cities/" + id), includes).build();
        return gson().fromJson(execute(url), CityResponse.class).getData();
    }

    public List<City> searchCities(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("cities/search/" + name), includes).build();
        return fetchPaged(base, CitiesResponse.class, CitiesResponse::getData, CitiesResponse::getPagination, 0);
    }

    // -------------------------------------------------------------------------
    // Types
    // -------------------------------------------------------------------------

    public List<Type> getAllTypes(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("types"), includes).build();
        return fetchPaged(base, TypesResponse.class, TypesResponse::getData, TypesResponse::getPagination, 0);
    }

    public Type getTypeById(long id) throws IOException, SportMonksException {
        HttpUrl url = coreUrl("types/" + id).build();
        return gson().fromJson(execute(url), TypeResponse.class).getData();
    }

    public List<Type> getTypesByEntity(String entity) throws IOException, SportMonksException {
        HttpUrl url = coreUrl("types/entities/" + entity).build();
        return gson().fromJson(execute(url), TypesResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Timezones
    // -------------------------------------------------------------------------

    public List<String> getAllTimezones() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("timezones").build();
        return gson().fromJson(execute(url), TimezonesResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Filters
    // -------------------------------------------------------------------------

    public Map<String, List<String>> getAllEntityFilters() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("filters/entities").build();
        return gson().fromJson(execute(url), FiltersResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // MySportmonks
    // -------------------------------------------------------------------------

    public MyApi getMyApi() throws IOException, SportMonksException {
        HttpUrl url = myUrl("api").build();
        return gson().fromJson(execute(url), MyApiResponse.class).getData();
    }

    public List<MyLeague> getMyLeagues(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(myUrl("leagues"), includes).build();
        return fetchPaged(base, MyLeaguesResponse.class, MyLeaguesResponse::getData, MyLeaguesResponse::getPagination, 0);
    }

    public List<String> getMyEnrichments() throws IOException, SportMonksException {
        HttpUrl url = myUrl("enrichments").build();
        return gson().fromJson(execute(url), MyEnrichmentsResponse.class).getData();
    }

    public List<MyResource> getMyResources() throws IOException, SportMonksException {
        HttpUrl url = myUrl("resources").build();
        return gson().fromJson(execute(url), MyResourcesResponse.class).getData();
    }

    public List<MyUsage> getMyUsage() throws IOException, SportMonksException {
        HttpUrl url = myUrl("usage").build();
        return gson().fromJson(execute(url), MyUsagesResponse.class).getData();
    }
}
