package aba.rest;

import java.util.List;

import aba.rest_vo.*;

public interface KoverProcessor {

	void process(KoverStateVO koverState, List<DetailVO> freeDetailVO);
	
}
