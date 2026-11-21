package es.com.kete1987.sportmonks.library.football.model.sport;

/** The sport an entity belongs to, returned by the {@code sport} include (always football here). */
public class Sport {
    private Long id;
    private String name;
    private String code;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
