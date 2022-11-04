package aba.puzzle.rest;


import aba.puzzle.domain.rest.mapstruct.dto.PuzzleConfigDto;
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleStateDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.server.PathParam;


/**
 * Created by alekseybarabanov on 14.08.16.
 */

//@RestController
public interface PuzzleRestService {
    @PostMapping(name = "/newPuzzleConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void newPuzzleConfig(@RequestBody PuzzleConfigDto newPuzzleConfig);

    @PostMapping(value = "/newCompletedPuzzle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void newCompletedPuzzle(@RequestBody PuzzleStateDto puzzleStateDto);

    @GetMapping(name = "/puzzleConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    PuzzleConfigDto getPuzzleConfig(@PathParam("configId") String configId);

    //    @RequestMapping(value="/greeting", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    //   ResponseEntity<Collection<DetailVO>> getDetails();
}
