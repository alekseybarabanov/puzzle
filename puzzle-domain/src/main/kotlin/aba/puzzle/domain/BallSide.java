package aba.puzzle.domain;

import java.util.Objects;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class BallSide {
    private Color color;
    private BallPart ballPart;

    public BallSide(Color color, BallPart ballPart) {
        this.color = color;
        this.ballPart = ballPart;
    }

    public BallPart getBallPart() {
        return ballPart;
    }

    public Color getColor() {
        return color;
    }

    public BallSide getComplement() {
        if (ballPart == BallPart.one_third) {
            return new BallSide(color, BallPart.two_thirds);
        } else {
            return new BallSide(color, BallPart.one_third);
        }
    }

    @Override
    public String toString() {
        return "BallSide{" +
                "color=" + color +
                ", ballPart=" + ballPart +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BallSide ballSide = (BallSide) o;
        return color == ballSide.color && ballPart == ballSide.ballPart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, ballPart);
    }
}
