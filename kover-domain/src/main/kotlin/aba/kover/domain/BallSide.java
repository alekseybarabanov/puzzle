package aba.kover.domain;

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
}
