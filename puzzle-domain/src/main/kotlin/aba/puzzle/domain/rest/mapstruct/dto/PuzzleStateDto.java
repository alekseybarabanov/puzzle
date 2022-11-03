package aba.puzzle.domain.rest.mapstruct.dto;

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

	public List<PuzzleStateEntryDto> getCoverage() {
		return coverage;
	}

	public void setCoverage(List<PuzzleStateEntryDto> coverage) {
		this.coverage = coverage;
	}

}
