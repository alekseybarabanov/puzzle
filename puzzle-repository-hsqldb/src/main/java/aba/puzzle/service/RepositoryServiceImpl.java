package aba.puzzle.service;

import aba.puzzle.domain.PuzzleConfig;
import aba.puzzle.domain.PuzzleState;
import aba.puzzle.mapper.PersistenceMapper;
import aba.puzzle.persistence_dto.PuzzleConfigDto;
import aba.puzzle.persistence_dto.PuzzleStateDto;
import aba.puzzle.repository.PuzzleConfigRepository;
import aba.puzzle.repository.PuzzleStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepositoryServiceImpl implements RepositoryService {
    @Autowired
    private PuzzleConfigRepository repository;
    @Autowired
    private PuzzleStateRepository stateRepository;
    @Autowired
    private PersistenceMapper mapper;

    @Override
    @Transactional
    public void storePuzzleConfig(PuzzleConfig puzzleConfig) {
        PuzzleConfigDto puzzleConfigDto = mapper.puzzleConfigToPuzzleConfigDto(puzzleConfig);
        repository.save(puzzleConfigDto);
        puzzleConfig.setId(puzzleConfigDto.getId());
    }

    @Override
    @Transactional
    public PuzzleConfig getPuzzleConfig(String puzzleConfigId) {
        PuzzleConfigSpecification spec = new PuzzleConfigSpecification(puzzleConfigId);
        return repository.findOne(spec)
                .map(mapper::puzzleConfigDtoToPuzzleConfig)
                .orElse(null);
    }

    @Override
    public void storePuzzleState(PuzzleState puzzleState) {
        storePuzzleConfig(puzzleState.getPuzzleConfig());
        PuzzleStateDto puzzleStateDto = mapper.puzzleStateToPuzzleStateDto(puzzleState);
        stateRepository.save(puzzleStateDto);
    }

    @Override
    public List<PuzzleState> getPuzzleState(String puzzleConfigId) {
        PuzzleStatesByConfigId spec = new PuzzleStatesByConfigId(puzzleConfigId);
        PuzzleConfig puzzleConfig = getPuzzleConfig(puzzleConfigId);
        return stateRepository.findAll(spec)
                .stream()
                .map(state -> mapper.puzzleStateDtoToPuzzleState(state, puzzleConfig))
                .collect(Collectors.toList());
    }

    static class PuzzleConfigSpecification implements Specification<PuzzleConfigDto> {
        private String extConfigId;

        PuzzleConfigSpecification(String extConfigId) {
            this.extConfigId = extConfigId;
        }

        @Override
        public Predicate toPredicate(Root<PuzzleConfigDto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            return builder.equal(root.get("extPuzzleConfigId"), extConfigId);
        }
    }

    static class PuzzleStatesByConfigId implements Specification<PuzzleStateDto> {
        private String configId;

        PuzzleStatesByConfigId(String configId) {
            this.configId = configId;
        }

        @Override
        public Predicate toPredicate(Root<PuzzleStateDto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.equal(root.get("puzzleConfigId"), configId);
        }
    }
}