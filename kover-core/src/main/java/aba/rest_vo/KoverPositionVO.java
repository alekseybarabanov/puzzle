package aba.rest_vo;

import aba.detail.KoverPosition;

public class KoverPositionVO {
	private int shiftX;
	private int shiftY;
	
	public static KoverPositionVO fromKoverPosition(KoverPosition koverPosition) {
		final KoverPositionVO koverPositionVO = new KoverPositionVO();
		koverPositionVO.shiftX = koverPosition.getShiftX();
		koverPositionVO.shiftY = koverPosition.getShiftY();
		return koverPositionVO;
	}
	
	public static KoverPosition toKoverPosition(KoverPositionVO koverPositionVO) {
		return KoverPosition.getByShifts(koverPositionVO.getShiftX(), koverPositionVO.getShiftY());
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
