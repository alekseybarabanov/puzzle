package aba.service;

import java.util.List;

import aba.detail.Detail;
import aba.persistence_vo.DetailVO;

public interface DetailService {

	List<Detail> getAll();
	
	List<DetailVO> getAllVO();

	void create(Detail detail);
}
