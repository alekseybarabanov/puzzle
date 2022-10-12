package aba.kover.core.rest_vo;

import java.util.HashMap;
import java.util.Map;

import aba.kover.core.detail.DetailWithRotation;
import aba.kover.core.detail.KoverPosition;

public class KoverStateEntryVO {
	private KoverPositionVO koverPositionVO;
	
	private DetailWithRotationVO detailWithRotationVO;
	
	public static KoverStateEntryVO fromKoverStateEntry(Map.Entry<KoverPosition, DetailWithRotation> entry) {
		final KoverStateEntryVO koverStateEntryVO = new KoverStateEntryVO();
		koverStateEntryVO.koverPositionVO = KoverPositionVO.fromKoverPosition(entry.getKey());
		koverStateEntryVO.detailWithRotationVO = DetailWithRotationVO.fromDetailWithRotation(entry.getValue());
		return koverStateEntryVO;
	}
	
	public static Map.Entry<KoverPosition, DetailWithRotation> toKoverStateEntry(KoverStateEntryVO koverStateEntryVO) {
		Map<KoverPosition, DetailWithRotation> map = new HashMap<>();
		map.put(KoverPositionVO.toKoverPosition(koverStateEntryVO.koverPositionVO), DetailWithRotationVO.toDetailWithRotation(koverStateEntryVO.detailWithRotationVO));
		return map.entrySet().iterator().next();
	}

	public KoverPositionVO getKoverPositionVO() {
		return koverPositionVO;
	}

	public void setKoverPositionVO(KoverPositionVO koverPositionVO) {
		this.koverPositionVO = koverPositionVO;
	}

	public DetailWithRotationVO getDetailWithRotationVO() {
		return detailWithRotationVO;
	}

	public void setDetailWithRotationVO(DetailWithRotationVO detailWithRotationVO) {
		this.detailWithRotationVO = detailWithRotationVO;
	}
}
