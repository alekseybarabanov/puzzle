package aba.puzzle.persistence_vo;

import javax.persistence.*;

import aba.puzzle.domain.BallSide;
import aba.puzzle.domain.Detail;
import aba.puzzle.domain.DetailSide;

@Entity(name="DETAIL")
public class DetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name="LEFT_SIDE")
	private BallSideVO left;
	@OneToOne
	@JoinColumn(name="UPPER_SIDE")
	private BallSideVO upper;
	@OneToOne
	@JoinColumn(name="RIGHT_SIDE")
	private BallSideVO right;
	@OneToOne
	@JoinColumn(name="LOWER_SIDE")
	private BallSideVO lower;

	public BallSideVO getLeft() {
		return left;
	}
	
	public static DetailVO fromDetail(Detail detail) {
		DetailVO result = new DetailVO();
		for (DetailSide side : DetailSide.values()) {
			BallSide ballSide = detail.getBallSide(side);
			result.setSide(BallSideVO.fromBallSide(ballSide), side);
		}
		return result;
	}
	
	public static Detail toDetail(DetailVO detailVO) {
		Detail result = new Detail(BallSideVO.toBallSide(detailVO.getLeft()),
				BallSideVO.toBallSide(detailVO.getUpper()),
				BallSideVO.toBallSide(detailVO.getRight()),
				BallSideVO.toBallSide(detailVO.getLower())
				);
		return result;
	}
	
	public void setSide(BallSideVO sideVO, DetailSide side) {
		switch (side) {
		case left:
			setLeft(sideVO);
			break;
		case up:
			setUpper(sideVO);
			break;
		case right:
			setRight(sideVO);
			break;
		case down:
			setLower(sideVO);
			break;
		}
	}

	public void setLeft(BallSideVO left) {
		this.left = left;
	}

	public BallSideVO getUpper() {
		return upper;
	}

	public void setUpper(BallSideVO upper) {
		this.upper = upper;
	}

	public BallSideVO getRight() {
		return right;
	}

	public void setRight(BallSideVO right) {
		this.right = right;
	}

	public BallSideVO getLower() {
		return lower;
	}

	public void setLower(BallSideVO lower) {
		this.lower = lower;
	}

}

