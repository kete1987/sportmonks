package es.com.kete1987.sportmonks.library.football.model.season;

import java.util.List;

public class KnockoutBracket {
    private List<BracketStage> stages;
    private List<BracketEdge> edges;

    public KnockoutBracket() {
    }

    public List<BracketStage> getStages() { return stages; }
    public List<BracketEdge> getEdges() { return edges; }
}
