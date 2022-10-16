package aba.puzzle.repository;

import aba.puzzle.persistence_vo.PuzzleConfigVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PuzzleRepository extends JpaRepository<PuzzleConfigVO, Integer>, JpaSpecificationExecutor<PuzzleConfigVO> {
	
};
