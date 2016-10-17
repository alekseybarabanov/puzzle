package aba.detail;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class BallSide {
    private Colors color;
    private BallPart ballPart;

    public BallSide(Colors color, BallPart ballPart) {
        this.color = color;
        this.ballPart = ballPart;
    }

    public BallPart getBallPart() {
        return ballPart;
    }

    public Colors getColor() {
        return color;
    }
}
