package es.com.kete1987.sportmonks.library.v2.model.league;

public class Country {
    private final CountryData data;

    public Country(CountryData data) {
        this.data = data;
    }

    public CountryData getData() {
        return data;
    }

    public class CountryData {
        private String name;
        private long id;

        public CountryData() {
        }

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }
    }
}
