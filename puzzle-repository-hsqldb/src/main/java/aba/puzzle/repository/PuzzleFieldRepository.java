package aba.puzzle.repository;

import aba.puzzle.persistence_vo.PuzzleFieldVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleFieldRepository extends JpaRepository<PuzzleFieldVO, Integer> {
}

