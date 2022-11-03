package aba.puzzle.domain.rest.mapstruct.dto;

import aba.puzzle.domain.DetailWithRotation;
import aba.puzzle.domain.PuzzleField;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleStateEntryDto {
	private PuzzleFieldDto puzzleFieldDto;
	
	private PuzzleDetailWithRotationDto puzzleDetailWithRotationDto;

	public PuzzleFieldDto getPuzzleFieldDto() {
		return puzzleFieldDto;
	}

	public void setPuzzleFieldDto(PuzzleFieldDto puzzleFieldDto) {
		this.puzzleFieldDto = puzzleFieldDto;
	}

	public PuzzleDetailWithRotationDto getDetailWithRotationDto() {
		return puzzleDetailWithRotationDto;
	}

	public void setDetailWithRotationVO(PuzzleDetailWithRotationDto puzzleDetailWithRotationDto) {
		this.puzzleDetailWithRotationDto = puzzleDetailWithRotationDto;
	}
}
