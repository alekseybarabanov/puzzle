package aba.puzzle.rest;

import aba.puzzle.domain.PuzzleConfig;
import aba.puzzle.domain.PuzzleState;
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleConfigDto;
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleStateDto;
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper;
import aba.puzzle.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alekseybarabanov on 14.08.16.
 */
@RestController
public class PuzzleRestServiceImpl implements PuzzleRestService {

    private MapStructMapper mapper = MapStructMapper.INSTANCE;

    @Autowired
    private RepositoryService repositoryService;

    @Override
    @PostMapping(value = "/newPuzzleConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void newPuzzleConfig(@RequestBody PuzzleConfigDto newPuzzleConfig) {
        PuzzleConfig puzzleConfig = mapper.puzzleConfigDtoToPuzzleConfig(newPuzzleConfig);
        repositoryService.storePuzzleConfig(puzzleConfig);
    }

    @Override
    @PostMapping(value = "/newCompletedPuzzle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void newCompletedPuzzle(@RequestBody PuzzleStateDto puzzleStateDto) {
        PuzzleState puzzleState = mapper.puzzleStateDtoToPuzzleState(puzzleStateDto);
        repositoryService.storePuzzleConfig(puzzleState.getPuzzleConfig());
        repositoryService.storePuzzleState(puzzleState);
    }

    @Override
    @GetMapping(value = "/puzzleConfig/{configId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PuzzleConfigDto getPuzzleConfig(@PathVariable("configId") String configId) {
        PuzzleConfig puzzleConfig = repositoryService.getPuzzleConfig(configId);
        if (puzzleConfig != null) {
            return mapper.puzzleConfigToPuzzleConfigDto(puzzleConfig);
        } else {
            return null;
        }
    }

}