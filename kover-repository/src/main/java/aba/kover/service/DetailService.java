package aba.kover.service;

import java.util.List;

import aba.kover.domain.Detail;
import aba.kover.persistence_vo.DetailVO;

public interface DetailService {

	List<Detail> getAll();
	
	List<DetailVO> getAllVO();

	void create(Detail detail);
}
