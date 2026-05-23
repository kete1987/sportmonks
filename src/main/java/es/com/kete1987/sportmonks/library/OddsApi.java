package es.com.kete1987.sportmonks.library;

import com.google.gson.Gson;
import es.com.kete1987.sportmonks.library.common.util.Constants;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.odds.model.Bookmaker;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMapping;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerMappingsResponse;
import es.com.kete1987.sportmonks.library.odds.model.BookmakerResponse;
import es.com.kete1987.sportmonks.library.odds.model.BookmakersResponse;
import es.com.kete1987.sportmonks.library.odds.model.Market;
import es.com.kete1987.sportmonks.library.odds.model.MarketResponse;
import es.com.kete1987.sportmonks.library.odds.model.MarketsResponse;
import es.com.kete1987.sportmonks.library.odds.model.Odd;
import es.com.kete1987.sportmonks.library.odds.model.OddsResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OddsApi extends SportMonksApiBase {

    private final String oddsBase;

    public OddsApi(String apiToken) {
        this(apiToken, null);
    }

    public OddsApi(String apiToken, String locale) {
        this(buildHttpClient(apiToken), Constants.baseURLOdds, locale, new RateLimitTracker());
    }

    OddsApi(OkHttpClient client, String oddsBase, String locale, RateLimitTracker tracker) {
        super(client, locale, tracker);
        this.oddsBase = oddsBase;
    }

    private HttpUrl.Builder oddsUrl(String path) {
        return localeUrl(HttpUrl.parse(oddsBase + path).newBuilder());
    }

    private List<Odd> oddsData(OddsResponse resp) {
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Pre-match odds
    // -------------------------------------------------------------------------

    public List<Odd> getMatchOdds(String matchId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(oddsUrl("pre-match/fixtures/" + matchId), includes).build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getMatchOddsByBookmaker(String matchId, String bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + matchId + "/bookmakers/" + bookmakerId).build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getMatchOddsByMarket(String matchId, String marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + matchId + "/markets/" + marketId).build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    // -------------------------------------------------------------------------
    // Markets
    // -------------------------------------------------------------------------

    public List<Market> getAllMarkets() throws IOException, SportMonksException {
        HttpUrl base = oddsUrl("markets").build();
        Gson g = gson();
        MarketsResponse resp = g.fromJson(execute(base), MarketsResponse.class);
        if (resp == null || resp.getData() == null) return new ArrayList<>();
        List<Market> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), MarketsResponse.class);
            if (resp != null && resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public Market getMarketById(long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("markets/" + marketId).build();
        MarketResponse resp = gson().fromJson(execute(url), MarketResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Market> searchMarkets(String query) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("markets/search/" + query).build();
        MarketsResponse resp = gson().fromJson(execute(url), MarketsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Bookmakers
    // -------------------------------------------------------------------------

    public List<Bookmaker> getAllBookmakers() throws IOException, SportMonksException {
        HttpUrl base = oddsUrl("bookmakers").build();
        Gson g = gson();
        BookmakersResponse resp = g.fromJson(execute(base), BookmakersResponse.class);
        if (resp == null || resp.getData() == null) return new ArrayList<>();
        List<Bookmaker> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = g.fromJson(execute(paged), BookmakersResponse.class);
            if (resp != null && resp.getData() != null) all.addAll(resp.getData());
        }
        return all;
    }

    public Bookmaker getBookmakerById(long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("bookmakers/" + bookmakerId).build();
        BookmakerResponse resp = gson().fromJson(execute(url), BookmakerResponse.class);
        return resp != null ? resp.getData() : null;
    }

    public List<Bookmaker> searchBookmakers(String query) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("bookmakers/search/" + query).build();
        BookmakersResponse resp = gson().fromJson(execute(url), BookmakersResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    public List<BookmakerMapping> getBookmakerMappingsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("bookmakers/mapping/fixtures/" + fixtureId).build();
        BookmakerMappingsResponse resp = gson().fromJson(execute(url), BookmakerMappingsResponse.class);
        return resp != null && resp.getData() != null ? resp.getData() : new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Inplay odds
    // -------------------------------------------------------------------------

    public List<Odd> getInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId).build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/bookmakers/" + bookmakerId).build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/markets/" + marketId).build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getLastUpdatedInplayOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/lastupdated").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    // -------------------------------------------------------------------------
    // Premium pre-match odds
    // -------------------------------------------------------------------------

    public List<Odd> getPremiumPreMatchOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + fixtureId + "/premium").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + fixtureId + "/bookmakers/" + bookmakerId + "/premium").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + fixtureId + "/markets/" + marketId + "/premium").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getLastUpdatedPremiumPreMatchOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/lastupdated/premium").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    // -------------------------------------------------------------------------
    // Premium inplay odds
    // -------------------------------------------------------------------------

    public List<Odd> getPremiumInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/premium").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getPremiumInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/bookmakers/" + bookmakerId + "/premium").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getPremiumInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/markets/" + marketId + "/premium").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }

    public List<Odd> getLastUpdatedPremiumInplayOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/lastupdated/premium").build();
        return oddsData(gson().fromJson(execute(url), OddsResponse.class));
    }
}
