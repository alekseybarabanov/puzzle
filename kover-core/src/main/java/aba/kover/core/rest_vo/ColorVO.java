package aba.kover.core.rest_vo;

import aba.kover.core.detail.Color;

public class ColorVO {
	
	private String wording;
	
	
	public String getColor() {
		return wording;
	}

	public void setColor(String color) {
		this.wording = color;
	}

	public static ColorVO fromColor(Color color) {
		ColorVO result = new ColorVO();
		result.setColor(color.toString());
		return result;
	}
	
	public static Color toColor(ColorVO colorVO) {
		return Color.valueOf(colorVO.getColor());
	}
}
