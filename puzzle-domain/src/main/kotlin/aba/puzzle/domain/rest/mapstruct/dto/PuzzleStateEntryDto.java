package aba.puzzle.domain.rest.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

public class PuzzleStateEntryDto {
    private PuzzleFieldDto puzzleFieldDto;

    private PuzzleDetailWithRotationDto puzzleDetailWithRotationDto;

    public PuzzleFieldDto getPuzzleFieldDto() {
        return puzzleFieldDto;
    }

    public void setPuzzleFieldDto(PuzzleFieldDto puzzleFieldDto) {
        this.puzzleFieldDto = puzzleFieldDto;
    }

    public PuzzleDetailWithRotationDto getPuzzleDetailWithRotationDto() {
        return puzzleDetailWithRotationDto;
    }

    public void setPuzzleDetailWithRotationDto(PuzzleDetailWithRotationDto puzzleDetailWithRotationDto) {
        this.puzzleDetailWithRotationDto = puzzleDetailWithRotationDto;
    }
}
