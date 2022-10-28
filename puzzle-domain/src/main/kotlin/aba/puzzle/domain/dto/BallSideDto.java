package aba.puzzle.domain.dto;


import aba.puzzle.domain.BallSide;

public class BallSideDto {
	
	private ColorDto color;
	private BallPartDto side;
	
	public static BallSideDto fromBallSide(BallSide ballSide) {
		BallSideDto result = new BallSideDto();
		ColorDto colorDto = ColorDto.fromColor(ballSide.getColor());
		BallPartDto sideVO = BallPartDto.fromBallPart(ballSide.getBallPart());
		result.setColor(colorDto);
		result.setSide(sideVO);
		return result;
	}
	
	public static BallSide toBallSide(BallSideDto ballSideDto) {
		BallSide result = new BallSide(ColorDto.toColor(ballSideDto.getColor()),
				BallPartDto.toBallPart(ballSideDto.getSide()));
		
		return result;
	}

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
