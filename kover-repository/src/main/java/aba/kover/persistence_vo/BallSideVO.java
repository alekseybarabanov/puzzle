package aba.kover.persistence_vo;

import javax.persistence.*;

import aba.kover.domain.BallSide;

@Entity(name="BALL_SIDE")
public class BallSideVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne
	@JoinColumn(name="COLOR")
	private ColorVO color;
	@OneToOne
	@JoinColumn(name="BALL_PART")
	private BallPartVO side;
	
	public static BallSideVO fromBallSide(BallSide ballSide) {
		BallSideVO result = new BallSideVO();
		ColorVO colorVO = ColorVO.fromColor(ballSide.getColor());
		BallPartVO sideVO = BallPartVO.fromBallPart(ballSide.getBallPart());
		result.setColor(colorVO);
		result.setSide(sideVO);
		return result;
	}
	
	public static BallSide toBallSide(BallSideVO ballSideVO) {
		BallSide result = new BallSide(ColorVO.toColor(ballSideVO.getColor()),
				BallPartVO.toBallPart(ballSideVO.getSide()));
		
		return result;
	}

	public ColorVO getColor() {
		return color;
	}

	public void setColor(ColorVO color) {
		this.color = color;
	}

	public BallPartVO getSide() {
		return side;
	}

	public void setSide(BallPartVO side) {
		this.side = side;
	}
	
}
