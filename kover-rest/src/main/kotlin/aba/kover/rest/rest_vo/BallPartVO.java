package aba.kover.rest.rest_vo;

import aba.kover.domain.BallPart;

public class BallPartVO {

	private String wording;
	
	public static BallPartVO fromBallPart(BallPart ballPart) {
		BallPartVO result = new BallPartVO();
		result.setSide(ballPart.name());
		return result;
	}
	
	public static BallPart toBallPart(BallPartVO sideVO) {
		return BallPart.valueOf(sideVO.getSide());
	}

	public String getSide() {
		return wording;
	}

	public void setSide(String side) {
		this.wording = side;
	}

}
