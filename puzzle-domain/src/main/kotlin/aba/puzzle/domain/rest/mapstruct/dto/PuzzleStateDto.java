package aba.puzzle.domain.rest.mapstruct.dto;

import java.util.ArrayList;
import java.util.List;

public class PuzzleStateDto {
    private Integer id = null;

    private boolean isCompleted = false;

    private PuzzleConfigDto puzzleConfigDto = null;
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

    public PuzzleConfigDto getPuzzleConfigDto() {
        return puzzleConfigDto;
    }

    public void setPuzzleConfigDto(PuzzleConfigDto puzzleConfigDto) {
        this.puzzleConfigDto = puzzleConfigDto;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
