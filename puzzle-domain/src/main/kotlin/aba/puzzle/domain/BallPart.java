package aba.puzzle.domain;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public enum BallPart {
    two_thirds,
    one_third;


    public static boolean isFull(BallPart part1, BallPart part2) {
        return (part1 == one_third && part2 == two_thirds)
                || (part1 == two_thirds && part2 == one_third);
    }
}
