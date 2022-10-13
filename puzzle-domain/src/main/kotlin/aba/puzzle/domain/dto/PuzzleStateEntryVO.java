package aba.puzzle.domain.dto;

import aba.puzzle.domain.DetailWithRotation;
import aba.puzzle.domain.PuzzlePosition;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleStateEntryVO {
	private PuzzlePositionVO puzzlePositionVO;
	
	private DetailWithRotationVO detailWithRotationVO;
	
	public static PuzzleStateEntryVO fromPuzzleStateEntry(Map.Entry<PuzzlePosition, DetailWithRotation> entry) {
		final PuzzleStateEntryVO puzzleStateEntryVO = new PuzzleStateEntryVO();
		puzzleStateEntryVO.puzzlePositionVO = PuzzlePositionVO.fromPuzzlePosition(entry.getKey());
		puzzleStateEntryVO.detailWithRotationVO = DetailWithRotationVO.fromDetailWithRotation(entry.getValue());
		return puzzleStateEntryVO;
	}
	
	public static Map.Entry<PuzzlePosition, DetailWithRotation> toPuzzleStateEntry(PuzzleStateEntryVO puzzleStateEntryVO) {
		Map<PuzzlePosition, DetailWithRotation> map = new HashMap<>();
		map.put(PuzzlePositionVO.toPuzzlePosition(puzzleStateEntryVO.puzzlePositionVO), DetailWithRotationVO.toDetailWithRotation(puzzleStateEntryVO.detailWithRotationVO));
		return map.entrySet().iterator().next();
	}

	public PuzzlePositionVO getPuzzlePositionVO() {
		return puzzlePositionVO;
	}

	public void setPuzzlePositionVO(PuzzlePositionVO puzzlePositionVO) {
		this.puzzlePositionVO = puzzlePositionVO;
	}

	public DetailWithRotationVO getDetailWithRotationVO() {
		return detailWithRotationVO;
	}

	public void setDetailWithRotationVO(DetailWithRotationVO detailWithRotationVO) {
		this.detailWithRotationVO = detailWithRotationVO;
	}
}
