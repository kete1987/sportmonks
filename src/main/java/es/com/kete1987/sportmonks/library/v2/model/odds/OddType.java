package es.com.kete1987.sportmonks.library.v2.model.odds;

import java.util.List;

public class OddType {
    private final String name = null;
    private final OddBookmakerData bookmaker = null;

    public OddType() {
    }

    public String getName() {
        return name;
    }

    public List<OddBookmaker> getBookmakers() {
        return bookmaker.getOddBookmakers();
    }
}
