package aba.puzzle.domain.dto;

import aba.puzzle.domain.DetailWithRotation;
import aba.puzzle.domain.PuzzleField;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleStateEntryVO {
	private PuzzleFieldVO puzzleFieldVO;
	
	private DetailWithRotationVO detailWithRotationVO;
	
	public static PuzzleStateEntryVO fromPuzzleStateEntry(Map.Entry<PuzzleField, DetailWithRotation> entry) {
		final PuzzleStateEntryVO puzzleStateEntryVO = new PuzzleStateEntryVO();
		puzzleStateEntryVO.puzzleFieldVO = PuzzleFieldVO.Companion.fromPuzzleField(entry.getKey());
		puzzleStateEntryVO.detailWithRotationVO = DetailWithRotationVO.fromDetailWithRotation(entry.getValue());
		return puzzleStateEntryVO;
	}
	
	public static Map.Entry<PuzzleField, DetailWithRotation> toPuzzleStateEntry(PuzzleStateEntryVO puzzleStateEntryVO) {
		Map<PuzzleField, DetailWithRotation> map = new HashMap<>();
		map.put(PuzzleFieldVO.Companion.toPuzzleField(puzzleStateEntryVO.puzzleFieldVO), DetailWithRotationVO.toDetailWithRotation(puzzleStateEntryVO.detailWithRotationVO));
		return map.entrySet().iterator().next();
	}

	public PuzzleFieldVO getPuzzleFieldVO() {
		return puzzleFieldVO;
	}

	public void setPuzzleFieldVO(PuzzleFieldVO puzzleFieldVO) {
		this.puzzleFieldVO = puzzleFieldVO;
	}

	public DetailWithRotationVO getDetailWithRotationVO() {
		return detailWithRotationVO;
	}

	public void setDetailWithRotationVO(DetailWithRotationVO detailWithRotationVO) {
		this.detailWithRotationVO = detailWithRotationVO;
	}
}
