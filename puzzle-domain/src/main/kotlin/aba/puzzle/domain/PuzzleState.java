package aba.puzzle.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class PuzzleState {

    private Integer id = null;

    private boolean isCompleted = false;

    private PuzzleConfig puzzleConfig = null;

    private Map<PuzzleField, DetailWithRotation> positionedDetails = new HashMap<PuzzleField, DetailWithRotation>();


    public PuzzleState(PuzzleConfig puzzleConfig) {
        this.puzzleConfig = puzzleConfig;
    }

    public PuzzleState(PuzzleConfig puzzleConfig, Map<PuzzleField, DetailWithRotation> positionedDetails) {
        this.puzzleConfig = puzzleConfig;
        this.positionedDetails.putAll(positionedDetails);
    }

    public PuzzleState(Integer id, PuzzleConfig puzzleConfig, PuzzleState puzzleState, PuzzleField position, DetailWithRotation detail) {
        this.id = id;
        this.puzzleConfig = puzzleConfig;
        this.positionedDetails.putAll(puzzleState.getPositionedDetails());
        this.positionedDetails.put(position, detail);
    }

    public Map<PuzzleField, DetailWithRotation> getPositionedDetails() {
        return positionedDetails;
    }

    public void setPositionedDetails(Map<PuzzleField, DetailWithRotation> positionedDetails) {
        this.positionedDetails = positionedDetails;
    }

    @Override
    public String toString() {
        return "PuzzleState{" +
                "positionedDetails=" + positionedDetails +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PuzzleConfig getPuzzleConfig() {
        return puzzleConfig;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
