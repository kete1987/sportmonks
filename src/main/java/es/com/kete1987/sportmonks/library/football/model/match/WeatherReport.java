package es.com.kete1987.sportmonks.library.football.model.match;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

/**
 * Weather at the venue of a fixture, as returned by the {@code weatherReport} include
 * (the API returns it under the key {@code weatherreport}).
 * <p><b>Partially verified shape:</b> modelled on a single real response (fixture 19732648,
 * {@code type} {@code "actual"}). Forecast reports and other matches may carry different keys,
 * so temperatures are exposed as raw key/value maps (e.g. {@code "current"}) and every field may
 * be absent or null. {@code humidity} and {@code clouds} arrive as strings with a percent sign
 * (e.g. {@code "61%"}).</p>
 */
public class WeatherReport {
    @Nullable
    private Long id;
    @Nullable
    @SerializedName("fixture_id")
    private Long fixtureId;
    @Nullable
    @SerializedName("venue_id")
    private Long venueId;
    @Nullable
    private Map<String, Double> temperature;
    @Nullable
    @SerializedName("feels_like")
    private Map<String, Double> feelsLike;
    @Nullable
    private Wind wind;
    @Nullable
    private String humidity;
    @Nullable
    private Double pressure;
    @Nullable
    private String clouds;
    @Nullable
    private String description;
    @Nullable
    private String icon;
    @Nullable
    private String type;
    @Nullable
    private String metric;
    @Nullable
    private Current current;

    /** @return the report ID, or null when unavailable. */
    @Nullable
    public Long getId() {
        return id;
    }

    /** @return the fixture ID, or null when unavailable. */
    @Nullable
    public Long getFixtureId() {
        return fixtureId;
    }

    /** @return the venue ID, or null when unavailable. */
    @Nullable
    public Long getVenueId() {
        return venueId;
    }

    /** @return unmodifiable temperatures by moment (e.g. {@code "current"}), or null when unavailable. */
    @Nullable
    public Map<String, Double> getTemperature() {
        return temperature == null ? null : Collections.unmodifiableMap(temperature);
    }

    /** @return unmodifiable apparent temperatures by moment (e.g. {@code "current"}), or null. */
    @Nullable
    public Map<String, Double> getFeelsLike() {
        return feelsLike == null ? null : Collections.unmodifiableMap(feelsLike);
    }

    /** @return the wind, or null when unavailable. */
    @Nullable
    public Wind getWind() {
        return wind;
    }

    /** @return the raw humidity (e.g. {@code "61%"}), or null when unavailable. */
    @Nullable
    public String getHumidity() {
        return humidity;
    }

    /** @return the atmospheric pressure (hPa), or null when unavailable. */
    @Nullable
    public Double getPressure() {
        return pressure;
    }

    /** @return the raw cloud cover (e.g. {@code "0%"}), or null when unavailable. */
    @Nullable
    public String getClouds() {
        return clouds;
    }

    /** @return the description (e.g. {@code "clear sky"}), or null when unavailable. */
    @Nullable
    public String getDescription() {
        return description;
    }

    /** @return the weather icon URL, or null when unavailable. */
    @Nullable
    public String getIcon() {
        return icon;
    }

    /** @return the report type (e.g. {@code "actual"}), or null when unavailable. */
    @Nullable
    public String getType() {
        return type;
    }

    /** @return the temperature unit as sent by the API (e.g. {@code "celcius"}), or null. */
    @Nullable
    public String getMetric() {
        return metric;
    }

    /** @return the current conditions, or null when unavailable. */
    @Nullable
    public Current getCurrent() {
        return current;
    }

    /** Wind of a {@link WeatherReport}. Every field may be absent or null. */
    public static class Wind {
        @Nullable
        private Double speed;
        @Nullable
        private Double direction;

        /** @return the wind speed, or null when unavailable. */
        @Nullable
        public Double getSpeed() {
            return speed;
        }

        /** @return the wind direction in degrees, or null when unavailable. */
        @Nullable
        public Double getDirection() {
            return direction;
        }
    }

    /** Current conditions of a {@link WeatherReport}. Every field may be absent or null. */
    public static class Current {
        @Nullable
        private Double temp;
        @Nullable
        private Double wind;
        @Nullable
        private String clouds;
        @Nullable
        private String humidity;
        @Nullable
        private Double pressure;
        @Nullable
        private Double direction;
        @Nullable
        @SerializedName("feels_like")
        private Double feelsLike;
        @Nullable
        private String description;

        /** @return the temperature, or null when unavailable. */
        @Nullable
        public Double getTemp() {
            return temp;
        }

        /** @return the wind speed, or null when unavailable. */
        @Nullable
        public Double getWind() {
            return wind;
        }

        /** @return the raw cloud cover (e.g. {@code "0%"}), or null when unavailable. */
        @Nullable
        public String getClouds() {
            return clouds;
        }

        /** @return the raw humidity (e.g. {@code "61%"}), or null when unavailable. */
        @Nullable
        public String getHumidity() {
            return humidity;
        }

        /** @return the atmospheric pressure (hPa), or null when unavailable. */
        @Nullable
        public Double getPressure() {
            return pressure;
        }

        /** @return the wind direction in degrees, or null when unavailable. */
        @Nullable
        public Double getDirection() {
            return direction;
        }

        /** @return the apparent temperature, or null when unavailable. */
        @Nullable
        public Double getFeelsLike() {
            return feelsLike;
        }

        /** @return the description, or null when unavailable. */
        @Nullable
        public String getDescription() {
            return description;
        }
    }
}
