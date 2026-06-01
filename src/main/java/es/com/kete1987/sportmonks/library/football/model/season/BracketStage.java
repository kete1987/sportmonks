package es.com.kete1987.sportmonks.library.football.model.season;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BracketStage {
    @SerializedName("stage_id")
    private Long stageId;
    @SerializedName("stage_name")
    private String stageName;
    private List<BracketFixture> fixtures;

    public BracketStage() {
    }

    public Long getStageId() { return stageId; }
    public String getStageName() { return stageName; }
    public List<BracketFixture> getFixtures() { return fixtures; }
}
