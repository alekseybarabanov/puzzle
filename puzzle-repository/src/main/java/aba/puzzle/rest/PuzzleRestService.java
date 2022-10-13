package aba.puzzle.rest;

import java.util.Collection;

import aba.puzzle.domain.dto.DetailVO;
import org.springframework.http.ResponseEntity;


/**
 * Created by alekseybarabanov on 14.08.16.
 */

//@RestController
public interface PuzzleRestService {

    //    @RequestMapping(value="/greeting", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<DetailVO>> getDetails();
}
