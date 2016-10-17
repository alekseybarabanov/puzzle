package aba.rest_vo;

import aba.detail.Colors;

public class ColorVO {
	
	private String wording;
	
	
	public String getColor() {
		return wording;
	}

	public void setColor(String color) {
		this.wording = color;
	}

	public static ColorVO fromColor(Colors color) {
		ColorVO result = new ColorVO();
		result.setColor(color.toString());
		return result;
	}
	
	public static Colors toColor(ColorVO colorVO) {
		return Colors.valueOf(colorVO.getColor());
	}
}
