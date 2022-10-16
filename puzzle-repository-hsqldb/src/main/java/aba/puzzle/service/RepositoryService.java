package aba.puzzle.service;

import aba.puzzle.domain.PuzzleConfig;

public interface RepositoryService {

    void storePuzzleConfig(PuzzleConfig puzzleConfig, String puzzleConfigId);

    PuzzleConfig getPuzzleConfig(String puzzleConfigId);
}
