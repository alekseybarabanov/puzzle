package aba.puzzle.service;

import aba.puzzle.domain.PuzzleConfig;
import aba.puzzle.domain.PuzzleState;

import java.util.List;

public interface RepositoryService {

    void storePuzzleConfig(PuzzleConfig puzzleConfig);

    PuzzleConfig getPuzzleConfig(String puzzleConfigId);

    void storePuzzleState(PuzzleState puzzleState);

    List<PuzzleState> getPuzzleState(String puzzleConfigId);
}
