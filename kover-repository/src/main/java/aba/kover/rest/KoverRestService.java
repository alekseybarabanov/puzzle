package aba.kover.rest;

import java.util.Collection;

import aba.kover.core.rest_vo.DetailVO;
import org.springframework.http.ResponseEntity;


/**
 * Created by alekseybarabanov on 14.08.16.
 */

//@RestController
public interface KoverRestService {

    //    @RequestMapping(value="/greeting", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<DetailVO>> getDetails();
}
