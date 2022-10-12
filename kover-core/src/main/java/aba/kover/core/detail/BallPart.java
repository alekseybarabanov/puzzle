package aba.kover.core.detail;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public enum BallPart {
    two_thirds(2),
    one_third(1);

    private int part;
    private static int sumOfParts = 3;

    BallPart(int part) {
        this.part = part;
    }

    public static boolean isFull(BallPart part1, BallPart part2) {
        return part1.part + part2.part == sumOfParts;
    }
}
