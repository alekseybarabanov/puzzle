package aba.puzzle.domain.dto;

import aba.puzzle.domain.DetailWithRotation;
import aba.puzzle.domain.PuzzleField;
import aba.puzzle.domain.PuzzleState;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleStateDto {
	private List<PuzzleStateEntryDto> coverage = new ArrayList<>();
	
	public static PuzzleStateDto fromPuzzleState(PuzzleState puzzleState) {
		final PuzzleStateDto result = new PuzzleStateDto();
		for (Map.Entry<PuzzleField, DetailWithRotation> entry : puzzleState.getPositionedDetails().entrySet()) {
			result.coverage.add(PuzzleStateEntryDto.fromPuzzleStateEntry(entry));
		}
		return result;		
	}
	
	public static PuzzleState toPuzzleState(PuzzleStateDto puzzleStateDto) {
		final PuzzleState puzzleState = new PuzzleState();
		final Map<PuzzleField, DetailWithRotation> resultMap = new HashMap<>();
		for (PuzzleStateEntryDto entry : puzzleStateDto.coverage) {
			resultMap.put(PuzzleFieldVO.Companion.toPuzzleField(entry.getPuzzleFieldVO()),
					PuzzleDetailWithRotationDto.toDetailWithRotation(entry.getDetailWithRotationVO()));
		}
		puzzleState.setPositionedDetails(resultMap);
		return puzzleState;
	}
}
