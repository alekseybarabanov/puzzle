package aba.detail;

import java.util.HashMap;
import java.util.Map;

import static aba.detail.DetailSide.*;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class Detail {
    private Map<DetailSide, BallSide> sides = new HashMap<DetailSide, BallSide>(4);

    public Detail(BallSide leftSide, BallSide upperSide, BallSide rightSide, BallSide lowerSide) {
        sides.put(left, leftSide);
        sides.put(upper, upperSide);
        sides.put(right, rightSide);
        sides.put(below, lowerSide);
    }

    public BallSide getBallSide(DetailSide side) {
        return sides.get(side);
    }
}
