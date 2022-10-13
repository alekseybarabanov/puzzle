package aba.puzzle.domain.dto;


import aba.puzzle.domain.BallSide;
import aba.puzzle.domain.Detail;
import aba.puzzle.domain.DetailSide;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DetailVO {

	private int id;
	private BallSideVO left;
	private BallSideVO upper;
	private BallSideVO right;
	private BallSideVO lower;

	public BallSideVO getLeft() {
		return left;
	}
	
	public static DetailVO fromDetail(Detail detail) {
		DetailVO result = new DetailVO();
		result.id = detail.getId();
		for (DetailSide side : DetailSide.values()) {
			BallSide ballSide = detail.getBallSide(side);
			result.setSide(BallSideVO.fromBallSide(ballSide), side);
		}
		return result;
	}
	
	public static Detail toDetail(DetailVO detailVO) {
		Detail result = new Detail(detailVO.id,
				BallSideVO.toBallSide(detailVO.getLeft()),
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
