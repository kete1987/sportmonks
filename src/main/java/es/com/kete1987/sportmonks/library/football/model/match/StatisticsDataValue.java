package es.com.kete1987.sportmonks.library.football.model.match;

public class StatisticsDataValue {
    private Double value;

    public StatisticsDataValue() {
    }

    /**
     * Valor truncado a entero. Devuelve {@code null} si la API no envía valor.
     * Para estadísticas con decimales (xG, porcentajes) usa {@link #getDoubleValue()}.
     */
    public Long getValue() {
        return value == null ? null : value.longValue();
    }

    /** Valor sin pérdida de decimales, o {@code null} si la API no envía valor. */
    public Double getDoubleValue() {
        return value;
    }
}
