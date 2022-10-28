package aba.puzzle.domain.dto;

import aba.puzzle.domain.DetailWithRotation;
import aba.puzzle.domain.PuzzleField;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleStateEntryDto {
	private PuzzleFieldDto puzzleFieldDto;
	
	private PuzzleDetailWithRotationDto puzzleDetailWithRotationDto;
	
	public static PuzzleStateEntryDto fromPuzzleStateEntry(Map.Entry<PuzzleField, DetailWithRotation> entry) {
		final PuzzleStateEntryDto puzzleStateEntryDto = new PuzzleStateEntryDto();
		puzzleStateEntryDto.puzzleFieldDto = PuzzleFieldDto.Companion.fromPuzzleField(entry.getKey());
		puzzleStateEntryDto.puzzleDetailWithRotationDto = PuzzleDetailWithRotationDto.fromDetailWithRotation(entry.getValue());
		return puzzleStateEntryDto;
	}
	
	public static Map.Entry<PuzzleField, DetailWithRotation> toPuzzleStateEntry(PuzzleStateEntryDto puzzleStateEntryDto) {
		Map<PuzzleField, DetailWithRotation> map = new HashMap<>();
		map.put(PuzzleFieldDto.Companion.toPuzzleField(puzzleStateEntryDto.puzzleFieldDto), PuzzleDetailWithRotationDto.toDetailWithRotation(puzzleStateEntryDto.puzzleDetailWithRotationDto));
		return map.entrySet().iterator().next();
	}

	public PuzzleFieldDto getPuzzleFieldVO() {
		return puzzleFieldDto;
	}

	public void setPuzzleFieldVO(PuzzleFieldDto puzzleFieldDto) {
		this.puzzleFieldDto = puzzleFieldDto;
	}

	public PuzzleDetailWithRotationDto getDetailWithRotationVO() {
		return puzzleDetailWithRotationDto;
	}

	public void setDetailWithRotationVO(PuzzleDetailWithRotationDto puzzleDetailWithRotationDto) {
		this.puzzleDetailWithRotationDto = puzzleDetailWithRotationDto;
	}
}
