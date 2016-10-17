package aba.rest_vo;

import aba.detail.Detail;
import aba.detail.DetailWithRotation;

public class DetailWithRotationVO {
    private DetailVO detail;
    private int rotation;

    public DetailWithRotationVO() {
    	
    }
    
    public static DetailWithRotationVO fromDetailWithRotation(DetailWithRotation detailWithRotation) {
    	final DetailWithRotationVO detailWithRotationVO = new DetailWithRotationVO();
    	detailWithRotationVO.detail = DetailVO.fromDetail(detailWithRotation.getDetail());
    	detailWithRotationVO.rotation = detailWithRotation.getRotation();
    	return detailWithRotationVO;
    }

    public static DetailWithRotation toDetailWithRotation(DetailWithRotationVO detailWithRotationVO) {
    	return new DetailWithRotation(
    			DetailVO.toDetail(detailWithRotationVO.getDetail()), detailWithRotationVO.getRotation());
    	
    }

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public DetailVO getDetail() {
		return detail;
	}

	public void setDetail(DetailVO detail) {
		this.detail = detail;
	}
}
