package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
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
import java.util.ArrayList;
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
        this(buildHttpClient(apiToken), Constants.BASE_URL_CORE, Constants.BASE_URL_MY, locale, new RateLimitTracker());
    }

    CoreApi(OkHttpClient client, String coreBase, String myBase, String locale, RateLimitTracker tracker) {
        super(client, locale, tracker);
        this.coreBase = coreBase;
        this.myBase = myBase;
    }

    private HttpUrl.Builder coreUrl(String path) {
        return localeUrl(HttpUrl.parse(coreBase + path).newBuilder());
    }

    private HttpUrl.Builder myUrl(String path) {
        return localeUrl(HttpUrl.parse(myBase + path).newBuilder());
    }

    // -------------------------------------------------------------------------
    // Continents
    // -------------------------------------------------------------------------

    public List<Continent> getAllContinents(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("continents"), includes).build();
        Gson g = gson();
        ContinentsResponse resp = g.fromJson(execute(base), ContinentsResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<Continent> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), ContinentsResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
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
        Gson g = gson();
        CountriesResponse resp = g.fromJson(execute(base), CountriesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<Country> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), CountriesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public Country getCountryById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("countries/" + id), includes).build();
        return gson().fromJson(execute(url), CountryResponse.class).getData();
    }

    public List<Country> searchCountries(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("countries/search/" + name), includes).build();
        Gson g = gson();
        CountriesResponse resp = g.fromJson(execute(base), CountriesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<Country> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), CountriesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    // -------------------------------------------------------------------------
    // Regions
    // -------------------------------------------------------------------------

    public List<Region> getAllRegions(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("regions"), includes).build();
        Gson g = gson();
        RegionsResponse resp = g.fromJson(execute(base), RegionsResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<Region> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), RegionsResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public Region getRegionById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("regions/" + id), includes).build();
        return gson().fromJson(execute(url), RegionResponse.class).getData();
    }

    public List<Region> searchRegions(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("regions/search/" + name), includes).build();
        Gson g = gson();
        RegionsResponse resp = g.fromJson(execute(base), RegionsResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<Region> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), RegionsResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    // -------------------------------------------------------------------------
    // Cities
    // -------------------------------------------------------------------------

    public List<City> getAllCities(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("cities"), includes).build();
        Gson g = gson();
        CitiesResponse resp = g.fromJson(execute(base), CitiesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<City> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), CitiesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public City getCityById(long id, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(coreUrl("cities/" + id), includes).build();
        return gson().fromJson(execute(url), CityResponse.class).getData();
    }

    public List<City> searchCities(String name, String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("cities/search/" + name), includes).build();
        Gson g = gson();
        CitiesResponse resp = g.fromJson(execute(base), CitiesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<City> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), CitiesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    // -------------------------------------------------------------------------
    // Types
    // -------------------------------------------------------------------------

    public List<Type> getAllTypes(String... includes) throws IOException, SportMonksException {
        HttpUrl base = withIncludes(coreUrl("types"), includes).build();
        Gson g = gson();
        TypesResponse resp = g.fromJson(execute(base), TypesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<Type> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), TypesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
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
        Gson g = gson();
        MyLeaguesResponse resp = g.fromJson(execute(base), MyLeaguesResponse.class);
        if (resp.getData() == null) return new ArrayList<>();
        List<MyLeague> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), MyLeaguesResponse.class);
            if (resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
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
