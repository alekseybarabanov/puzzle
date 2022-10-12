package aba.kover.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class Detail {
    private Map<DetailSide, BallSide> sides = new HashMap<DetailSide, BallSide>(4);

    public Detail(BallSide leftSide, BallSide upperSide, BallSide rightSide, BallSide lowerSide) {
        sides.put(DetailSide.left, leftSide);
        sides.put(DetailSide.up, upperSide);
        sides.put(DetailSide.right, rightSide);
        sides.put(DetailSide.down, lowerSide);
    }

    public BallSide getBallSide(DetailSide side) {
        return sides.get(side);
    }
}
