package aba.puzzle.domain.dto;

import aba.puzzle.domain.PuzzlePosition;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzlePositionVO {
	private int shiftX;
	private int shiftY;
	
	public static PuzzlePositionVO fromPuzzlePosition(PuzzlePosition puzzlePosition) {
		final PuzzlePositionVO puzzlePositionVO = new PuzzlePositionVO();
		puzzlePositionVO.shiftX = puzzlePosition.getShiftX();
		puzzlePositionVO.shiftY = puzzlePosition.getShiftY();
		return puzzlePositionVO;
	}
	
	public static PuzzlePosition toPuzzlePosition(PuzzlePositionVO puzzlePositionVO) {
		return PuzzlePosition.getByShifts(puzzlePositionVO.getShiftX(), puzzlePositionVO.getShiftY());
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
}
