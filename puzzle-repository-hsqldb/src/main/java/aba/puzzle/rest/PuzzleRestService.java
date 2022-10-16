package aba.puzzle.rest;

import java.util.Collection;

import aba.puzzle.domain.dto.DetailVO;
import aba.puzzle.domain.dto.NewTaskVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    void newPuzzleConfig(@RequestBody NewTaskVO newPuzzleConfig);

    @GetMapping(name = "/puzzleConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    NewTaskVO getPuzzleConfig(@PathParam("configId") String configId);

    //    @RequestMapping(value="/greeting", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
 //   ResponseEntity<Collection<DetailVO>> getDetails();
}
