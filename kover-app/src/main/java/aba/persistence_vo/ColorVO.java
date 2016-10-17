package aba.persistence_vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import aba.detail.Colors;

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

	public static ColorVO fromColor(Colors color) {
		ColorVO result = new ColorVO();
		result.setColor(color.toString());
		return result;
	}
	
	public static Colors toColor(ColorVO colorVO) {
		return Colors.valueOf(colorVO.getColor());
	}
}
