package es.com.kete1987.sportmonks.library.v2.model.match;

public class MatchScore {
    private final int localteam_score = -1;
    private final int visitorteam_score = -1;
    private final int localteam_pen_score = -1;
    private final int visitorteam_pen_score = -1;
    private final String ht_score = null;
    private final String ft_score = null;
    private final String et_score = null;

    public MatchScore() {
    }

    public int getLocalTeamScore() {
        return localteam_score;
    }

    public int getVisitorTeamScore() {
        return visitorteam_score;
    }

    public int getLocalTeamPenScore() {
        return localteam_pen_score;
    }

    public int getVisitorTeamPenScore() {
        return visitorteam_pen_score;
    }

    public String getHtScore() {
        return ht_score;
    }

    public String getFtScore() {
        return ft_score;
    }

    public String getEtScore() {
        return et_score;
    }


}
