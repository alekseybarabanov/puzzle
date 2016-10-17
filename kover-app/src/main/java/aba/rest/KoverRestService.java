package aba.rest;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import aba.rest_vo.DetailVO;


/**
 * Created by alekseybarabanov on 14.08.16.
 */

//@RestController
public interface KoverRestService {

    //    @RequestMapping(value="/greeting", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<DetailVO>> getDetails();
}
