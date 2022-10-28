package aba.puzzle.domain.dto;

import aba.puzzle.domain.DetailWithRotation;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleDetailWithRotationDto {
    private PuzzleDetailDto detail;
    private int rotation;

    public PuzzleDetailWithRotationDto() {
    	
    }
    
    public static PuzzleDetailWithRotationDto fromDetailWithRotation(DetailWithRotation detailWithRotation) {
    	final PuzzleDetailWithRotationDto puzzleDetailWithRotationDto = new PuzzleDetailWithRotationDto();
    	puzzleDetailWithRotationDto.detail = PuzzleDetailDto.fromDetail(detailWithRotation.getDetail());
    	puzzleDetailWithRotationDto.rotation = detailWithRotation.getRotation();
    	return puzzleDetailWithRotationDto;
    }

    public static DetailWithRotation toDetailWithRotation(PuzzleDetailWithRotationDto puzzleDetailWithRotationDto) {
    	return new DetailWithRotation(
    			PuzzleDetailDto.toDetail(puzzleDetailWithRotationDto.getDetail()), puzzleDetailWithRotationDto.getRotation());
    	
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
