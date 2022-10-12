package aba.kover.core.detail;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public enum DetailSide {

    left(0),
    up(1),
    right(2),
    down(3);

    private int position;

    DetailSide(int position) {
        this.position = position;
    }

    public static DetailSide getRotatedDetailSide(DetailSide side, int rotate) {
        return getDetailSideByPosition(side.position + rotate);
    }

    private static DetailSide getDetailSideByPosition(int position) {
        int sideIndex = position % 4;
        switch (sideIndex) {
            case 0:
                return left;
            case 1:
                return up;
            case 2:
                return right;
            case 3:
                return down;
            default:
                return null;
        }
    }

    public static DetailSide getMatchingSide(DetailSide side) {
        switch (side) {
            case left:
                return right;
            case up:
                return down;
            case right:
                return left;
            case down:
                return up;
        }
        return null;
    }
}
