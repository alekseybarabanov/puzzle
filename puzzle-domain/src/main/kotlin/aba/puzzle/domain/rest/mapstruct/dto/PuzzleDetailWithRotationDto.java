package aba.puzzle.domain.rest.mapstruct.dto;

import aba.puzzle.domain.DetailWithRotation;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleDetailWithRotationDto {
    private PuzzleDetailDto detail;
    private int rotation;

    public PuzzleDetailWithRotationDto() {
    	
    }
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public PuzzleDetailDto getDetail() {
		return detail;
	}

	public void setDetail(PuzzleDetailDto detail) {
		this.detail = detail;
	}
}
