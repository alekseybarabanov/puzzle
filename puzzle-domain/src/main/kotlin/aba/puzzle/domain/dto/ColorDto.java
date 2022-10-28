package aba.puzzle.domain.dto;

import aba.puzzle.domain.Color;

public class ColorDto {
	
	private String wording;
	
	
	public String getColor() {
		return wording;
	}

	public void setColor(String color) {
		this.wording = color;
	}

	public static ColorDto fromColor(Color color) {
		ColorDto result = new ColorDto();
		result.setColor(color.toString());
		return result;
	}
	
	public static Color toColor(ColorDto colorDto) {
		return Color.valueOf(colorDto.getColor());
	}
}
