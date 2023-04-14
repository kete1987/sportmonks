package es.com.kete1987.sportmonks.library.v3.model.match;

public class State {
    private Long id;
    private String state;
    private String name;
    private String short_name;
    private Long type_id; //TODO Comprobar 18/abril si se ha eliminado
    private Type type;

    public State() {
    }

    public Long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return short_name;
    }

    public Long getTypeId() {
        return type_id;
    }

    public Type getType() {
        return type;
    }
}
