package aba.puzzle.repository;

import aba.puzzle.persistence_dto.PuzzleStateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PuzzleStateRepository extends JpaRepository<PuzzleStateDto, Integer>, JpaSpecificationExecutor<PuzzleStateDto> {

};
