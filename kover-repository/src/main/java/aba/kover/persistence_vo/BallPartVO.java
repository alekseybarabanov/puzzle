package aba.kover.persistence_vo;

import javax.persistence.*;

import aba.kover.core.detail.BallPart;

@Entity(name = "BALL_PART")
public class BallPartVO {

	@Id
	@JoinColumn(name="WORDING")
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
