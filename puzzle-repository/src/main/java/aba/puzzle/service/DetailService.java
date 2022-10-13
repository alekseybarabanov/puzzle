package aba.puzzle.service;

import java.util.List;

import aba.puzzle.domain.Detail;
import aba.puzzle.persistence_vo.DetailVO;

public interface DetailService {

	List<Detail> getAll();
	
	List<DetailVO> getAllVO();

	void create(Detail detail);
}
