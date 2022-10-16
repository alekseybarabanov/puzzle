package aba.puzzle.repository;

import aba.puzzle.persistence_vo.PuzzleDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleDetailRepository extends JpaRepository<PuzzleDetailVO, Integer> {
}
