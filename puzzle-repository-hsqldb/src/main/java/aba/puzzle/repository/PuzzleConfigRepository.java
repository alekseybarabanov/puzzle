package aba.puzzle.repository;

import aba.puzzle.persistence_dto.PuzzleConfigDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PuzzleConfigRepository extends JpaRepository<PuzzleConfigDto, Integer>, JpaSpecificationExecutor<PuzzleConfigDto> {

};
