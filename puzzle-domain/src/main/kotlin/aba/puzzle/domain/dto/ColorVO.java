package aba.puzzle.domain.dto;

import aba.puzzle.domain.Color;

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