package aba.puzzle.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class PuzzleState {

	public PuzzleState() {
	}
	public PuzzleState(Map<PuzzlePosition, DetailWithRotation> positionedDetails) {
		this.positionedDetails.putAll(positionedDetails);
	}
	public PuzzleState(PuzzleState puzzleState, PuzzlePosition position, DetailWithRotation detail) {
		this.positionedDetails.putAll(puzzleState.getPositionedDetails());
		this.positionedDetails.put(position, detail);
	}
    private Map<PuzzlePosition, DetailWithRotation> positionedDetails = new HashMap<PuzzlePosition, DetailWithRotation>();

    public Map<PuzzlePosition, DetailWithRotation> getPositionedDetails() {
		return positionedDetails;
	}

	public void setPositionedDetails(Map<PuzzlePosition, DetailWithRotation> positionedDetails) {
		this.positionedDetails = positionedDetails;
	}

}
