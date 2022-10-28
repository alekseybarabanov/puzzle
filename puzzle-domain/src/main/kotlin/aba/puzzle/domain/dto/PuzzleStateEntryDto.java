package aba.puzzle.domain.dto;

import aba.puzzle.domain.DetailWithRotation;
import aba.puzzle.domain.PuzzleField;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleStateEntryDto {
	private PuzzleFieldVO puzzleFieldVO;
	
	private PuzzleDetailWithRotationDto puzzleDetailWithRotationDto;
	
	public static PuzzleStateEntryDto fromPuzzleStateEntry(Map.Entry<PuzzleField, DetailWithRotation> entry) {
		final PuzzleStateEntryDto puzzleStateEntryDto = new PuzzleStateEntryDto();
		puzzleStateEntryDto.puzzleFieldVO = PuzzleFieldVO.Companion.fromPuzzleField(entry.getKey());
		puzzleStateEntryDto.puzzleDetailWithRotationDto = PuzzleDetailWithRotationDto.fromDetailWithRotation(entry.getValue());
		return puzzleStateEntryDto;
	}
	
	public static Map.Entry<PuzzleField, DetailWithRotation> toPuzzleStateEntry(PuzzleStateEntryDto puzzleStateEntryDto) {
		Map<PuzzleField, DetailWithRotation> map = new HashMap<>();
		map.put(PuzzleFieldVO.Companion.toPuzzleField(puzzleStateEntryDto.puzzleFieldVO), PuzzleDetailWithRotationDto.toDetailWithRotation(puzzleStateEntryDto.puzzleDetailWithRotationDto));
		return map.entrySet().iterator().next();
	}

	public PuzzleFieldVO getPuzzleFieldVO() {
		return puzzleFieldVO;
	}

	public void setPuzzleFieldVO(PuzzleFieldVO puzzleFieldVO) {
		this.puzzleFieldVO = puzzleFieldVO;
	}

	public PuzzleDetailWithRotationDto getDetailWithRotationVO() {
		return puzzleDetailWithRotationDto;
	}

	public void setDetailWithRotationVO(PuzzleDetailWithRotationDto puzzleDetailWithRotationDto) {
		this.puzzleDetailWithRotationDto = puzzleDetailWithRotationDto;
	}
}
