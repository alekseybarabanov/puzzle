package aba.puzzle.domain.rest.mapstruct.dto;

import aba.puzzle.domain.BallPart;

public class BallPartDto {

	private String wording;

	public String getSide() {
		return wording;
	}

	public void setSide(String side) {
		this.wording = side;
	}

	public void setBallPart(String side) {
		this.wording = side;
	}

}
