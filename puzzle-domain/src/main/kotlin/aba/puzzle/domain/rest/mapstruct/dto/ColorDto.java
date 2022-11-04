package aba.puzzle.domain.rest.mapstruct.dto;

import aba.puzzle.domain.Color;

public class ColorDto {

    private String wording;


    public String getColor() {
        return wording;
    }

    public void setColor(String color) {
        this.wording = color;
    }

    public static Color toColor(ColorDto colorDto) {
        return Color.valueOf(colorDto.getColor());
    }
}
