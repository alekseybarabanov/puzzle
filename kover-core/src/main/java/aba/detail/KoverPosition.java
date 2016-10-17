package aba.detail;

import java.util.*;

import static aba.detail.DetailSide.*;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public enum KoverPosition {

    left_bottom(0,0),
    left_middle(0,1),
    left_upper(0,2),
    middle_bottom(1,0),
    middle_middle(1,1),
    middle_upper(1,2),
    right_bottom(2,0),
    right_middle(2,1),
    right_upper(2,2);

    private int shiftX;
    private int shiftY;

    KoverPosition(int shiftX, int shiftY) {
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    public static Map<KoverPosition, DetailSide> getDependentPositions(KoverPosition position) {
        Map<KoverPosition, DetailSide> result = new HashMap<KoverPosition, DetailSide>();
        switch (position) {
            case left_bottom:
                result.put(left_middle, upper);
                result.put(middle_bottom, right);
                break;
            case left_middle:
                result.put(left_bottom, below);
                result.put(left_upper, upper);
                result.put(middle_middle, right);
                break;
            case left_upper:
                result.put(left_middle, below);
                result.put(middle_upper, right);
                break;
            case middle_bottom:
                result.put(left_bottom, left);
                result.put(middle_middle, upper);
                result.put(right_bottom, right);
                break;
            case middle_middle:
                result.put(left_middle, left);
                result.put(middle_bottom, below);
                result.put(middle_upper, upper);
                result.put(right_middle, right);
                break;
            case middle_upper:
                result.put(left_upper, left);
                result.put(middle_middle, below);
                result.put(right_upper, right);
                break;
            case right_bottom:
                result.put(middle_bottom, left);
                result.put(right_middle, upper);
                break;
            case right_middle:
                result.put(middle_middle, left);
                result.put(right_bottom, below);
                result.put(right_upper, upper);
                break;
            case right_upper:
                result.put(middle_upper, left);
                result.put(right_middle, below);
                break;
            default:
        }
        return result;
    }

	public int getShiftX() {
		return shiftX;
	}

	public void setShiftX(int shiftX) {
		this.shiftX = shiftX;
	}

	public int getShiftY() {
		return shiftY;
	}

	public void setShiftY(int shiftY) {
		this.shiftY = shiftY;
	}
	
	public static KoverPosition getByShifts(int shiftX, int shiftY) {
		for (KoverPosition koverPosition : KoverPosition.values()) {
			if (koverPosition.getShiftX() == shiftX 
					&& koverPosition.getShiftY() == shiftY) {
				return koverPosition;
			}
		}
		return null;
	}
}
