package aba.rest;

import aba.rest_vo.FieldVO;

import java.util.List;

import aba.rest_vo.*;

public interface KoverProcessor {

	void process(FieldVO fieldVO, List<DetailVO> freeDetailVO);
	
}
