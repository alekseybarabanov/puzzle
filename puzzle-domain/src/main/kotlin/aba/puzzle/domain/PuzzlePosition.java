package aba.puzzle.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public enum PuzzlePosition {

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

    PuzzlePosition(int shiftX, int shiftY) {
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    public static Map<PuzzlePosition, DetailSide> getDependentPositions(PuzzlePosition position) {
        Map<PuzzlePosition, DetailSide> result = new HashMap<PuzzlePosition, DetailSide>();
        switch (position) {
            case left_bottom:
                result.put(left_middle, DetailSide.up);
                result.put(middle_bottom, DetailSide.right);
                break;
            case left_middle:
                result.put(left_bottom, DetailSide.down);
                result.put(left_upper, DetailSide.up);
                result.put(middle_middle, DetailSide.right);
                break;
            case left_upper:
                result.put(left_middle, DetailSide.down);
                result.put(middle_upper, DetailSide.right);
                break;
            case middle_bottom:
                result.put(left_bottom, DetailSide.left);
                result.put(middle_middle, DetailSide.up);
                result.put(right_bottom, DetailSide.right);
                break;
            case middle_middle:
                result.put(left_middle, DetailSide.left);
                result.put(middle_bottom, DetailSide.down);
                result.put(middle_upper, DetailSide.up);
                result.put(right_middle, DetailSide.right);
                break;
            case middle_upper:
                result.put(left_upper, DetailSide.left);
                result.put(middle_middle, DetailSide.down);
                result.put(right_upper, DetailSide.right);
                break;
            case right_bottom:
                result.put(middle_bottom, DetailSide.left);
                result.put(right_middle, DetailSide.up);
                break;
            case right_middle:
                result.put(middle_middle, DetailSide.left);
                result.put(right_bottom, DetailSide.down);
                result.put(right_upper, DetailSide.up);
                break;
            case right_upper:
                result.put(middle_upper, DetailSide.left);
                result.put(right_middle, DetailSide.down);
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
	
	public static PuzzlePosition getByShifts(int shiftX, int shiftY) {
		for (PuzzlePosition puzzlePosition : PuzzlePosition.values()) {
			if (puzzlePosition.getShiftX() == shiftX 
					&& puzzlePosition.getShiftY() == shiftY) {
				return puzzlePosition;
			}
		}
		return null;
	}
}
