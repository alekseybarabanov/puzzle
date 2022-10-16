package aba.puzzle.service;

import aba.puzzle.domain.PuzzleConfig;
import aba.puzzle.persistence_vo.PuzzleConfigVO;
import aba.puzzle.repository.PuzzleDetailRepository;
import aba.puzzle.repository.PuzzleFieldRepository;
import aba.puzzle.repository.PuzzleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private PuzzleRepository repository;

    @Autowired
    private PuzzleDetailRepository puzzleDetailRepository;

    @Autowired
    private PuzzleFieldRepository puzzleFieldRepository;

    @Override
    @Transactional
    public void storePuzzleConfig(PuzzleConfig puzzleConfig, String puzzleConfigId) {
        PuzzleConfigVO puzzleConfigVO = PuzzleConfigVO.fromPuzzleConfig(puzzleConfig, puzzleConfigId);
        repository.save(puzzleConfigVO);
        puzzleDetailRepository.saveAll(puzzleConfigVO.getPuzzleDetailVOS());
        puzzleFieldRepository.saveAll(puzzleConfigVO.getPuzzleFieldVOS());
    }

    @Override
    @Transactional
    public PuzzleConfig getPuzzleConfig(String puzzleConfigId) {
        PuzzleConfigSpecification spec = new PuzzleConfigSpecification(puzzleConfigId);
        return repository.findOne(spec)
                .map(PuzzleConfigVO::toPuzzleConfig)
                .orElse(null);
    }

    static class PuzzleConfigSpecification implements Specification<PuzzleConfigVO> {
        private String extConfigId;

        PuzzleConfigSpecification(String extConfigId) {
            this.extConfigId = extConfigId;
        }

        @Override
        public Predicate toPredicate(Root<PuzzleConfigVO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            return builder.equal(root.get("extPuzzleConfigId"), extConfigId);
        }
    }
}