package aba.kover.core.detail;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class KoverState {

	public KoverState() {
	}
	public KoverState(Map<KoverPosition, DetailWithRotation> positionedDetails) {
		this.positionedDetails.putAll(positionedDetails);
	}
	public KoverState(KoverState koverState, KoverPosition position, DetailWithRotation detail) {
		this.positionedDetails.putAll(koverState.getPositionedDetails());
		this.positionedDetails.put(position, detail);
	}
    private Map<KoverPosition, DetailWithRotation> positionedDetails = new HashMap<KoverPosition, DetailWithRotation>();

    public Map<KoverPosition, DetailWithRotation> getPositionedDetails() {
		return positionedDetails;
	}

	public void setPositionedDetails(Map<KoverPosition, DetailWithRotation> positionedDetails) {
		this.positionedDetails = positionedDetails;
	}

}
