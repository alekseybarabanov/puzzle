package aba.puzzle.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class PuzzleState {

	private Integer id = null;

	private Map<PuzzleField, DetailWithRotation> positionedDetails = new HashMap<PuzzleField, DetailWithRotation>();


	public PuzzleState() {
	}
	public PuzzleState(Map<PuzzleField, DetailWithRotation> positionedDetails) {
		this.positionedDetails.putAll(positionedDetails);
	}
	public PuzzleState(Integer id, PuzzleState puzzleState, PuzzleField position, DetailWithRotation detail) {
		this.id = id;
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
}
