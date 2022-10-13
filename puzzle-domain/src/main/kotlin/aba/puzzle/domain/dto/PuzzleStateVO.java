package aba.puzzle.domain.dto;

import aba.puzzle.domain.DetailWithRotation;
import aba.puzzle.domain.PuzzlePosition;
import aba.puzzle.domain.PuzzleState;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleStateVO {
	private List<PuzzleStateEntryVO> coverage = new ArrayList<>();
	
	public static PuzzleStateVO fromPuzzleState(PuzzleState puzzleState) {
		final PuzzleStateVO result = new PuzzleStateVO();
		for (Map.Entry<PuzzlePosition, DetailWithRotation> entry : puzzleState.getPositionedDetails().entrySet()) {
			result.coverage.add(PuzzleStateEntryVO.fromPuzzleStateEntry(entry));
		}
		return result;		
	}
	
	public static PuzzleState toPuzzleState(PuzzleStateVO puzzleStateVO) {
		final PuzzleState puzzleState = new PuzzleState();
		final Map<PuzzlePosition, DetailWithRotation> resultMap = new HashMap<>();
		for (PuzzleStateEntryVO entry : puzzleStateVO.coverage) {
			resultMap.put(PuzzlePositionVO.toPuzzlePosition(entry.getPuzzlePositionVO()), 
					DetailWithRotationVO.toDetailWithRotation(entry.getDetailWithRotationVO()));
		}
		puzzleState.setPositionedDetails(resultMap);
		return puzzleState;
	}
}
