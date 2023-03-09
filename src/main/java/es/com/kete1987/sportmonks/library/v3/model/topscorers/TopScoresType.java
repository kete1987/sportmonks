package es.com.kete1987.sportmonks.library.v3.model.topscorers;

public class TopScoresType {
    private Long id;
    private String name;
    private String code;
    private String developer_name;
    private String model_type;
    private String stat_group;

    public TopScoresType() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDeveloper_name() {
        return developer_name;
    }

    public String getModel_type() {
        return model_type;
    }

    public String getStat_group() {
        return stat_group;
    }
}
