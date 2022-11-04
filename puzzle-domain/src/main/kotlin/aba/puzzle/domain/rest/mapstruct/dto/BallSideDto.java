package aba.puzzle.domain.rest.mapstruct.dto;


public class BallSideDto {

    private ColorDto color;
    private BallPartDto side;

    public ColorDto getColor() {
        return color;
    }

    public void setColor(ColorDto color) {
        this.color = color;
    }

    public BallPartDto getSide() {
        return side;
    }

    public void setSide(BallPartDto side) {
        this.side = side;
    }

}
