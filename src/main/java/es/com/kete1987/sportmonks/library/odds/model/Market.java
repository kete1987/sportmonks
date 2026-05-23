package es.com.kete1987.sportmonks.library.odds.model;

public class Market {
    private Long id;
    private String name;
    private String display_name;
    private Boolean has_winning_calculations;
    private String developer_name;

    public Market() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return display_name;
    }

    public Boolean getHasWinningCalculations() {
        return has_winning_calculations;
    }

    public String getDeveloperName() {
        return developer_name;
    }
}
