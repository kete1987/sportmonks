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
import es.com.kete1987.sportmonks.library.core.model.my.MyEnrichment;
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
import es.com.kete1987.sportmonks.library.core.model.type.TypeEntitiesResponse;
import es.com.kete1987.sportmonks.library.core.model.type.TypeEntity;
import es.com.kete1987.sportmonks.library.core.model.type.TypeResponse;
import es.com.kete1987.sportmonks.library.core.model.type.TypesResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.Collections;
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
        this(apiToken, locale, timezone, ClientOptions.defaults());
    }

    /**
     * Creates the client with custom HTTP settings (timeouts, an own {@code OkHttpClient}).
     * Pass {@code null} for the default locale or timezone.
     */
    public CoreApi(String apiToken, String locale, String timezone, ClientOptions options) {
        this(buildHttpClient(apiToken, options), Constants.BASE_URL_CORE, Constants.BASE_URL_MY, locale, timezone, new RateLimitTracker());
    }

    CoreApi(OkHttpClient client, String coreBase, String myBase, String locale, String timezone, RateLimitTracker tracker) {
        super(client, locale, timezone, tracker);
        this.coreBase = coreBase;
        this.myBase = myBase;
    }

    private HttpUrl.Builder coreUrl(String path, Object... segments) {
        return apiUrl(coreBase, path, segments);
    }

    private HttpUrl.Builder myUrl(String path, Object... segments) {
        return apiUrl(myBase, path, segments);
    }

    // -------------------------------------------------------------------------
    // Continents
    // -------------------------------------------------------------------------

    public List<Continent> getAllContinents(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("continents"), includes).build();
        return fetchPaged(base, ContinentsResponse.class, ContinentsResponse::getData, ContinentsResponse::getPagination, 0);
    }

    public Continent getContinentById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("continents", id), includes).build();
        return get(url, ContinentResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Countries
    // -------------------------------------------------------------------------

    public List<Country> getAllCountries(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("countries"), includes).build();
        return fetchPaged(base, CountriesResponse.class, CountriesResponse::getData, CountriesResponse::getPagination, 0);
    }

    public Country getCountryById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("countries", id), includes).build();
        return get(url, CountryResponse.class).getData();
    }

    public List<Country> searchCountries(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("countries/search", name), includes).build();
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
        HttpUrl url = withIncludes(coreUrl("regions", id), includes).build();
        return get(url, RegionResponse.class).getData();
    }

    public List<Region> searchRegions(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("regions/search", name), includes).build();
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
        HttpUrl url = withIncludes(coreUrl("cities", id), includes).build();
        return get(url, CityResponse.class).getData();
    }

    public List<City> searchCities(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("cities/search", name), includes).build();
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
        HttpUrl url = coreUrl("types", id).build();
        return get(url, TypeResponse.class).getData();
    }

    /**
     * The types of every entity ({@code core/types/entities}), keyed by entity name
     * ({@code Event}, {@code Lineup}, ...). Empty when the response carries no data.
     */
    public Map<String, TypeEntity> getAllTypeEntities() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("types/entities").build();
        TypeEntitiesResponse response = get(url, TypeEntitiesResponse.class);
        Map<String, TypeEntity> data = response == null ? null : response.getData();
        return data == null ? Collections.emptyMap() : data;
    }

    /**
     * The types of one entity, taken from {@link #getAllTypeEntities()} (the API has no route per
     * entity). The name is matched ignoring case; an unknown entity gives an empty list.
     */
    public List<Type> getTypesByEntity(String entity) throws IOException, SportMonksException {
        for (Map.Entry<String, TypeEntity> entry : getAllTypeEntities().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(entity) && entry.getValue().getTypes() != null) {
                return entry.getValue().getTypes();
            }
        }
        return Collections.emptyList();
    }

    // -------------------------------------------------------------------------
    // Timezones
    // -------------------------------------------------------------------------

    public List<String> getAllTimezones() throws IOException, SportMonksException {
        HttpUrl url = coreUrl("timezones").build();
        return get(url, TimezonesResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Filters
    // -------------------------------------------------------------------------

    public Map<String, List<String>> getAllEntityFilters() throws IOException, SportMonksException {
        HttpUrl url = myUrl("filters/entity").build();
        return get(url, FiltersResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // MySportmonks
    // -------------------------------------------------------------------------

    public List<MyLeague> getMyLeagues(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(myUrl("leagues"), includes).build();
        return fetchPaged(base, MyLeaguesResponse.class, MyLeaguesResponse::getData, MyLeaguesResponse::getPagination, 0);
    }

    public List<MyEnrichment> getMyEnrichments() throws IOException, SportMonksException {
        HttpUrl url = myUrl("enrichments").build();
        return get(url, MyEnrichmentsResponse.class).getData();
    }

    public List<MyResource> getMyResources() throws IOException, SportMonksException {
        HttpUrl url = myUrl("resources").build();
        return get(url, MyResourcesResponse.class).getData();
    }

    /** Every usage row of the account: walks all pages, which can be many requests. */
    public List<MyUsage> getMyUsage() throws IOException, SportMonksException {
        return getMyUsage(0);
    }

    /** The newest {@code limit} usage rows; a non-positive {@code limit} means all of them. */
    public List<MyUsage> getMyUsage(int limit) throws IOException, SportMonksException {
        HttpUrl base = myUrl("usage").build();
        return fetchPaged(base, MyUsagesResponse.class, MyUsagesResponse::getData, MyUsagesResponse::getPagination, limit);
    }
}
