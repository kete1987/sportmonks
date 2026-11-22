package es.com.kete1987.sportmonks.library.core.model.my;

/** A resource (endpoint) the subscription can call, as listed by {@code my/resources}. */
public class MyResource {
    private Long id;
    private String description;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}