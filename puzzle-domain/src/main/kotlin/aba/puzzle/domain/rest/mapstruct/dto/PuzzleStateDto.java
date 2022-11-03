package aba.puzzle.domain.rest.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PuzzleStateDto {
	private Integer id = null;
	private List<PuzzleStateEntryDto> coverage = new ArrayList<>();

	public List<PuzzleStateEntryDto> getCoverage() {
		return coverage;
	}

	public void setCoverage(List<PuzzleStateEntryDto> coverage) {
		this.coverage = coverage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
