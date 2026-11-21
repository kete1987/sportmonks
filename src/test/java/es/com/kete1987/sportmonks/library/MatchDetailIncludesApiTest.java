package es.com.kete1987.sportmonks.library;

import com.google.gson.JsonElement;
import es.com.kete1987.sportmonks.library.common.util.SportMonksException;
import es.com.kete1987.sportmonks.library.football.model.coach.Coach;
import es.com.kete1987.sportmonks.library.football.model.match.EventData;
import es.com.kete1987.sportmonks.library.football.model.match.FixtureMetadata;
import es.com.kete1987.sportmonks.library.football.model.match.FixtureReferee;
import es.com.kete1987.sportmonks.library.football.model.match.FixtureSidelined;
import es.com.kete1987.sportmonks.library.football.model.match.MatchDetail;
import es.com.kete1987.sportmonks.library.football.model.match.WeatherReport;
import es.com.kete1987.sportmonks.library.football.util.RefereeType;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Fixture trimmed from the real response of fixture 19732648 (Barcelona - Real Madrid, 2026-10-25). */
class MatchDetailIncludesApiTest extends BaseApiTest {

    private static final String[] INCLUDES = {
            "metadata", "sidelined.sideline", "sidelined.player", "referees.referee", "coaches", "timeline", "weatherReport"};

    @Test
    void getMatchDetail_sendsIncludesAndParsesMetadata() throws IOException, SportMonksException, InterruptedException {
        enqueue("fixture_detail_extra_includes.json");

        MatchDetail match = api.getMatchDetail("19732648", INCLUDES);

        HttpUrl url = server.takeRequest().getRequestUrl();
        assertEquals("/fixtures/19732648", url.encodedPath());
        assertEquals(new HashSet<>(Arrays.asList(INCLUDES)),
                new HashSet<>(Arrays.asList(url.queryParameter("include").split(";"))));

        List<FixtureMetadata> metadata = match.getMetadata();
        assertEquals(5, metadata.size());
        FixtureMetadata neutral = metadata.get(0);
        assertEquals(10106163L, neutral.getId());
        assertEquals(19732648L, neutral.getMetadatableId());
        assertEquals(35L, neutral.getTypeId());
        assertEquals("object", neutral.getValueType());
        assertFalse(neutral.getValue("neutral").getAsBoolean());
        assertEquals("false", neutral.getValueAsString("neutral"));

        FixtureMetadata formation = match.getMetadataByTypeId(159);
        assertEquals("4-3-3", formation.getValueAsString("home"));
        assertEquals("4-2-3-1", formation.getValueAsString("away"));
        assertNull(formation.getValue("missing"));
        assertEquals("home", match.getMetadataByTypeId(36).getValueAsString("kickOff"));
        assertEquals(62065, match.getMetadataByTypeId(578).getValue("attendance").getAsInt());
        assertNull(match.getMetadataByTypeId(1));

        // values can also be a plain primitive
        FixtureMetadata hashtag = match.getMetadataByTypeId(613);
        assertEquals("string", hashtag.getValueType());
        assertEquals("#BARRMA", hashtag.getValues().getAsString());
        assertNull(hashtag.getValue("anything"));
        assertNull(hashtag.getValueAsString("anything"));

        // returned JSON is a copy: mutating it does not alter the model
        formation.getValues().getAsJsonObject().addProperty("home", "changed");
        assertEquals("4-3-3", formation.getValueAsString("home"));
        assertThrows(UnsupportedOperationException.class, () -> metadata.add(new FixtureMetadata()));
    }

    @Test
    void getMatchDetail_parsesSidelinedRefereesAndCoaches() throws IOException, SportMonksException {
        enqueue("fixture_detail_extra_includes.json");

        MatchDetail match = api.getMatchDetail("19732648", INCLUDES);

        List<FixtureSidelined> sidelined = match.getSidelined();
        assertEquals(2, sidelined.size());
        FixtureSidelined rodrygo = sidelined.get(0);
        assertEquals(8038692L, rodrygo.getId());
        assertEquals(19732648L, rodrygo.getFixtureId());
        assertEquals(772482L, rodrygo.getSidelineId());
        assertEquals(3468L, rodrygo.getParticipantId());
        assertEquals(7346228L, rodrygo.getPlayerId());
        assertEquals(557L, rodrygo.getTypeId());
        assertEquals("injury", rodrygo.getSideline().getCategory());
        assertEquals(37L, rodrygo.getSideline().getGamesMissed());
        assertEquals("Rodrygo", rodrygo.getPlayer().getDisplayName());
        assertNull(sidelined.get(1).getSideline());
        assertNull(sidelined.get(1).getPlayer());

        List<FixtureReferee> referees = match.getReferees();
        assertEquals(4, referees.size());
        assertEquals(RefereeType.REFEREE, referees.get(0).getTypeId().intValue());
        assertEquals(RefereeType.FIRST_ASSISTANT, referees.get(1).getTypeId().intValue());
        assertEquals(RefereeType.SECOND_ASSISTANT, referees.get(2).getTypeId().intValue());
        assertEquals(RefereeType.FOURTH_OFFICIAL, referees.get(3).getTypeId().intValue());
        FixtureReferee main = match.getMainReferee();
        assertSame(referees.get(0), main);
        assertEquals(15712L, main.getRefereeId());
        assertEquals("Juan Martínez Munuera", main.getReferee().getName());
        assertNull(referees.get(3).getReferee());
        assertFalse(referees.get(3).isMainReferee());

        List<Coach> coaches = match.getCoaches();
        assertEquals(2, coaches.size());
        Coach flick = coaches.get(0);
        assertEquals(37352367L, flick.getId());
        assertEquals("H. Flick", flick.getCommonName());
        assertEquals("Hans-Dieter Flick", flick.getDisplayName());
        assertEquals("https://cdn.sportmonks.com/images/soccer/coaches/15/37352367.png", flick.getImagePath());
        assertEquals(83L, flick.getMeta().getParticipantId());
        assertEquals(19732648L, flick.getMeta().getFixtureId());
        assertEquals(37352367L, flick.getMeta().getCoachId());
        assertEquals(3468L, coaches.get(1).getMeta().getParticipantId());
        assertThrows(UnsupportedOperationException.class, () -> coaches.add(new Coach()));
    }

    @Test
    void getMatchDetail_parsesTimelineAndWeatherReport() throws IOException, SportMonksException {
        enqueue("fixture_detail_extra_includes.json");

        MatchDetail match = api.getMatchDetail("19732648", INCLUDES);

        List<EventData> timeline = match.getTimeline();
        assertEquals(2, timeline.size());
        assertEquals(158410500L, timeline.get(0).getId());
        assertEquals("timeline", timeline.get(0).getSection());
        assertEquals("1st Corner", timeline.get(0).getAddition());
        assertEquals(19L, timeline.get(0).getMinute());
        assertEquals(1L, timeline.get(0).getSortOrder());
        assertNull(timeline.get(0).getPlayerId());
        assertNull(match.getEvents());

        WeatherReport weather = match.getWeatherReport();
        assertEquals(810646L, weather.getId());
        assertEquals(19732648L, weather.getFixtureId());
        assertEquals(9236L, weather.getVenueId());
        assertEquals(17.04, weather.getTemperature().get("current"));
        assertEquals(16.39, weather.getFeelsLike().get("current"));
        assertEquals(3.6, weather.getWind().getSpeed());
        assertEquals(330.0, weather.getWind().getDirection());
        assertEquals("61%", weather.getHumidity());
        assertEquals(1020.0, weather.getPressure());
        assertEquals("0%", weather.getClouds());
        assertEquals("clear sky", weather.getDescription());
        assertEquals("https://cdn.sportmonks.com/images/weather/01n.png", weather.getIcon());
        assertEquals("actual", weather.getType());
        assertEquals("celcius", weather.getMetric());
        WeatherReport.Current current = weather.getCurrent();
        assertEquals(17.04, current.getTemp());
        assertEquals(3.6, current.getWind());
        assertEquals("0%", current.getClouds());
        assertEquals("61%", current.getHumidity());
        assertEquals(1020.0, current.getPressure());
        assertEquals(330.0, current.getDirection());
        assertEquals(16.39, current.getFeelsLike());
        assertEquals("clear sky", current.getDescription());
        assertThrows(UnsupportedOperationException.class, () -> weather.getTemperature().put("day", 1.0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"",
            ",\"metadata\":null,\"sidelined\":null,\"referees\":null,\"coaches\":null,\"timeline\":null,\"weatherreport\":null"})
    void absentOrNullIncludesRemainNull(String relations) throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody("{\"data\":{\"id\":19732648" + relations + "}}"));

        MatchDetail match = api.getMatchDetail("19732648");

        assertNull(match.getMetadata());
        assertNull(match.getMetadataByTypeId(159));
        assertNull(match.getSidelined());
        assertNull(match.getReferees());
        assertNull(match.getMainReferee());
        assertNull(match.getCoaches());
        assertNull(match.getTimeline());
        assertNull(match.getWeatherReport());
    }

    @Test
    void emptyIncludesRemainEmptyAndUnmodifiable() throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody(
                "{\"data\":{\"metadata\":[],\"sidelined\":[],\"referees\":[],\"coaches\":[],\"timeline\":[]}}"));

        MatchDetail match = api.getMatchDetail("19732648", INCLUDES);

        assertTrue(match.getMetadata().isEmpty());
        assertTrue(match.getSidelined().isEmpty());
        assertTrue(match.getReferees().isEmpty());
        assertTrue(match.getCoaches().isEmpty());
        assertTrue(match.getTimeline().isEmpty());
        assertNull(match.getMetadataByTypeId(159));
        assertNull(match.getMainReferee());
        assertNull(match.getWeatherReport());
        List<FixtureReferee> referees = match.getReferees();
        FixtureReferee referee = new FixtureReferee();
        assertThrows(UnsupportedOperationException.class, () -> referees.add(referee));
    }

    @Test
    void camelCaseWeatherReportKeyIsAccepted() throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody(
                "{\"data\":{\"weatherReport\":{\"id\":1,\"temperature\":{\"day\":20.5,\"night\":11.0}}}}"));

        WeatherReport weather = api.getMatchDetail("1", "weatherReport").getWeatherReport();

        assertEquals(1L, weather.getId());
        assertEquals(20.5, weather.getTemperature().get("day"));
        assertNull(weather.getFeelsLike());
        assertNull(weather.getWind());
        assertNull(weather.getCurrent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}",
            "{\"id\":null,\"metadatable_id\":null,\"type_id\":null,\"value_type\":null,\"values\":null}"})
    void incompleteMetadataToleratesMissingAndNullFields(String entry) throws IOException, SportMonksException {
        server.enqueue(new MockResponse().setBody("{\"data\":{\"metadata\":[" + entry + "]}}"));

        FixtureMetadata metadata = api.getMatchDetail("1", "metadata").getMetadata().get(0);

        assertNull(metadata.getId());
        assertNull(metadata.getMetadatableId());
        assertNull(metadata.getTypeId());
        assertNull(metadata.getValueType());
        JsonElement values = metadata.getValues();
        assertNull(values);
        assertNull(metadata.getValue("home"));
    }
}
