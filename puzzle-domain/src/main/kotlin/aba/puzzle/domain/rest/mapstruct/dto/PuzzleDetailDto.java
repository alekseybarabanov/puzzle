package aba.puzzle.domain.rest.mapstruct.dto;


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

	public void setId(int id) {
		this.id = id;
	}

	public void setAllowedRotations(List<Integer> allowedRotations) {
		this.allowedRotations = allowedRotations;
	}

	public int getId() {
		return id;
	}

	public List<Integer> getAllowedRotations() {
		return allowedRotations;
	}

}

