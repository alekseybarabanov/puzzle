package aba.puzzle.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class PuzzleState {

	public PuzzleState() {
	}
	public PuzzleState(Map<PuzzleField, DetailWithRotation> positionedDetails) {
		this.positionedDetails.putAll(positionedDetails);
	}
	public PuzzleState(PuzzleState puzzleState, PuzzleField position, DetailWithRotation detail) {
		this.positionedDetails.putAll(puzzleState.getPositionedDetails());
		this.positionedDetails.put(position, detail);
	}
    private Map<PuzzleField, DetailWithRotation> positionedDetails = new HashMap<PuzzleField, DetailWithRotation>();

    public Map<PuzzleField, DetailWithRotation> getPositionedDetails() {
		return positionedDetails;
	}

	public void setPositionedDetails(Map<PuzzleField, DetailWithRotation> positionedDetails) {
		this.positionedDetails = positionedDetails;
	}

}
