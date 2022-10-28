package aba.puzzle.domain.dto;

import aba.puzzle.domain.BallPart;

public class BallPartDto {

	private String wording;
	
	public static BallPartDto fromBallPart(BallPart ballPart) {
		BallPartDto result = new BallPartDto();
		result.setSide(ballPart.name());
		return result;
	}
	
	public static BallPart toBallPart(BallPartDto sideVO) {
		return BallPart.valueOf(sideVO.getSide());
	}

	public String getSide() {
		return wording;
	}

	public void setSide(String side) {
		this.wording = side;
	}

}
