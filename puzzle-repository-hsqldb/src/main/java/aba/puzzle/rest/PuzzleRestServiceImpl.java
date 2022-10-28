package aba.puzzle.rest;

import aba.puzzle.domain.PuzzleConfig;
import aba.puzzle.domain.dto.NewTaskDto;
import aba.puzzle.domain.dto.PuzzleConfigDto;
import aba.puzzle.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alekseybarabanov on 14.08.16.
 */
@RestController
public class PuzzleRestServiceImpl implements PuzzleRestService {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    @PostMapping(value = "/newPuzzleConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void newPuzzleConfig(@RequestBody NewTaskDto newPuzzleConfig) {
        PuzzleConfig puzzleConfig = PuzzleConfigDto.Companion.toPuzzleConfig(newPuzzleConfig.getPuzzleConfig());
        repositoryService.storePuzzleConfig(puzzleConfig, newPuzzleConfig.getTopic());
    }

    @Override
    @GetMapping(value = "/puzzleConfig/{configId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NewTaskDto getPuzzleConfig(@PathVariable("configId") String configId) {
        PuzzleConfig puzzleConfig = repositoryService.getPuzzleConfig(configId);
        if (puzzleConfig != null) {
            return new NewTaskDto(configId, PuzzleConfigDto.Companion.fromPuzzleConfig(puzzleConfig));
        } else {
            return null;
        }
    }

    @GetMapping("/test")
    public String test() {
        return "ok";
    }
}