package es.com.kete1987.sportmonks.library.football.model.team;

import org.jetbrains.annotations.Nullable;

public class TeamMeta {
    private String location;
    @Nullable
    private Boolean winner;
    private String position;

    public TeamMeta() {

    }

    public String getLocation() {
        return location;
    }

    /**
     * @return {@code true} if the team won the fixture, {@code false} if it did not, or null when the
     *         API does not send it or sends null. It used to be a {@code String} holding "true" or
     *         "false".
     */
    @Nullable
    public Boolean getWinner() {
        return winner;
    }

    /** @return true only when the API says the team won; null (unknown) counts as false. */
    public boolean isWinner() {
        return Boolean.TRUE.equals(winner);
    }

    public String getPosition() {
        return position;
    }
}
