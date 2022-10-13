package aba.kover.rest;

import aba.kover.domain.Detail;
import aba.kover.domain.dto.DetailVO;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import aba.kover.service.DetailService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by alekseybarabanov on 14.08.16.
 */
@RestController
public class KoverRestServiceImpl implements KoverRestService {
	
	@Autowired
	private DetailService detailService;

    @RequestMapping(value="/details", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<DetailVO>> getDetails() {
    	final List<Detail> details = detailService.getAll();
    	final List<DetailVO> detailVOs = new ArrayList<>();
    	for (Detail detail : details) {
    		detailVOs.add(DetailVO.fromDetail(detail));
    	}
        return new ResponseEntity<Collection<DetailVO>>(detailVOs, HttpStatus.OK);
    }
}
