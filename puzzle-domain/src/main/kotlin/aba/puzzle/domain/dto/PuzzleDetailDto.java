package aba.puzzle.domain.dto;


import aba.puzzle.domain.BallSide;
import aba.puzzle.domain.Detail;
import aba.puzzle.domain.DetailSide;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleDetailDto {

	private int id;
	private BallSideDto left;
	private BallSideDto upper;
	private BallSideDto right;
	private BallSideDto lower;

	private List<Integer> allowedRotations;

	public BallSideDto getLeft() {
		return left;
	}
	
	public static PuzzleDetailDto fromDetail(Detail detail) {
		PuzzleDetailDto result = new PuzzleDetailDto();
		result.id = detail.getId();
		for (DetailSide side : DetailSide.values()) {
			BallSide ballSide = detail.getBallSide(side);
			result.setSide(BallSideDto.fromBallSide(ballSide), side);
		}
		result.allowedRotations = new ArrayList<>(detail.getAllowedRotations());
		return result;
	}
	
	public static Detail toDetail(PuzzleDetailDto puzzleDetailDto) {
		Detail result = new Detail(puzzleDetailDto.id,
				BallSideDto.toBallSide(puzzleDetailDto.getLeft()),
				BallSideDto.toBallSide(puzzleDetailDto.getUpper()),
				BallSideDto.toBallSide(puzzleDetailDto.getRight()),
				BallSideDto.toBallSide(puzzleDetailDto.getLower())
				);
		if (puzzleDetailDto.allowedRotations != null) {
			result.setAllowedRotations(puzzleDetailDto.allowedRotations);
		}
		return result;
	}
	
	public void setSide(BallSideDto sideVO, DetailSide side) {
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

	public void setLeft(BallSideDto left) {
		this.left = left;
	}

	public BallSideDto getUpper() {
		return upper;
	}

	public void setUpper(BallSideDto upper) {
		this.upper = upper;
	}

	public BallSideDto getRight() {
		return right;
	}

	public void setRight(BallSideDto right) {
		this.right = right;
	}

	public BallSideDto getLower() {
		return lower;
	}

	public void setLower(BallSideDto lower) {
		this.lower = lower;
	}

}

