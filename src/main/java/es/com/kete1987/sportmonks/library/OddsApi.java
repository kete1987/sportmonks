package es.com.kete1987.sportmonks.library;

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

    // -------------------------------------------------------------------------
    // Pre-match odds
    // -------------------------------------------------------------------------

    public List<Odd> getMatchOdds(String matchId, String... includes) throws IOException, SportMonksException {
        HttpUrl url = withIncludes(oddsUrl("pre-match/fixtures/" + matchId), includes).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getMatchOddsByBookmaker(String matchId, String bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + matchId + "/bookmakers/" + bookmakerId).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getMatchOddsByMarket(String matchId, String marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + matchId + "/markets/" + marketId).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Markets
    // -------------------------------------------------------------------------

    public List<Market> getAllMarkets() throws IOException, SportMonksException {
        HttpUrl base = oddsUrl("markets").build();
        MarketsResponse resp = gson().fromJson(execute(base), MarketsResponse.class);
        List<Market> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = gson().fromJson(execute(paged), MarketsResponse.class);
            all.addAll(resp.getData());
        }
        return all;
    }

    public Market getMarketById(long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("markets/" + marketId).build();
        return gson().fromJson(execute(url), MarketResponse.class).getData();
    }

    public List<Market> searchMarkets(String query) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("markets/search/" + query).build();
        return gson().fromJson(execute(url), MarketsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Bookmakers
    // -------------------------------------------------------------------------

    public List<Bookmaker> getAllBookmakers() throws IOException, SportMonksException {
        HttpUrl base = oddsUrl("bookmakers").build();
        BookmakersResponse resp = gson().fromJson(execute(base), BookmakersResponse.class);
        List<Bookmaker> all = new ArrayList<>(resp.getData());
        int page = 1;
        while (resp.getPagination() != null && resp.getPagination().hasMore()) {
            page++;
            HttpUrl paged = base.newBuilder().addQueryParameter("page", String.valueOf(page)).build();
            resp = gson().fromJson(execute(paged), BookmakersResponse.class);
            all.addAll(resp.getData());
        }
        return all;
    }

    public Bookmaker getBookmakerById(long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("bookmakers/" + bookmakerId).build();
        return gson().fromJson(execute(url), BookmakerResponse.class).getData();
    }

    public List<Bookmaker> searchBookmakers(String query) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("bookmakers/search/" + query).build();
        return gson().fromJson(execute(url), BookmakersResponse.class).getData();
    }

    public List<BookmakerMapping> getBookmakerMappingsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("bookmakers/mapping/fixtures/" + fixtureId).build();
        return gson().fromJson(execute(url), BookmakerMappingsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Inplay odds
    // -------------------------------------------------------------------------

    public List<Odd> getInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/bookmakers/" + bookmakerId).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/markets/" + marketId).build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getLastUpdatedInplayOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/lastupdated").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Premium pre-match odds
    // -------------------------------------------------------------------------

    public List<Odd> getPremiumPreMatchOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + fixtureId + "/premium").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + fixtureId + "/bookmakers/" + bookmakerId + "/premium").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getPremiumPreMatchOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/fixtures/" + fixtureId + "/markets/" + marketId + "/premium").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getLastUpdatedPremiumPreMatchOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("pre-match/lastupdated/premium").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    // -------------------------------------------------------------------------
    // Premium inplay odds
    // -------------------------------------------------------------------------

    public List<Odd> getPremiumInplayOddsByFixture(long fixtureId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/premium").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getPremiumInplayOddsByFixtureAndBookmaker(long fixtureId, long bookmakerId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/bookmakers/" + bookmakerId + "/premium").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getPremiumInplayOddsByFixtureAndMarket(long fixtureId, long marketId) throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/fixtures/" + fixtureId + "/markets/" + marketId + "/premium").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }

    public List<Odd> getLastUpdatedPremiumInplayOdds() throws IOException, SportMonksException {
        HttpUrl url = oddsUrl("inplay/lastupdated/premium").build();
        return gson().fromJson(execute(url), OddsResponse.class).getData();
    }
}
