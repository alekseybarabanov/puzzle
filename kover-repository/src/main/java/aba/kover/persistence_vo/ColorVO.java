package aba.kover.persistence_vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import aba.kover.core.detail.Color;

@Entity(name="COLOR")
public class ColorVO {
	
	@Id
	@JoinColumn(name="WORDING")
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
