package aba.puzzle.domain;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class Detail {
    private int id;
    private Map<DetailSide, BallSide> sides = new HashMap<DetailSide, BallSide>(4);

    @NotNull
    private List<Integer> allowedRotations = new ArrayList<>();

    public Detail(int id, BallSide leftSide, BallSide upperSide, BallSide rightSide, BallSide lowerSide) {
        this.id = id;
        sides.put(DetailSide.left, leftSide);
        sides.put(DetailSide.up, upperSide);
        sides.put(DetailSide.right, rightSide);
        sides.put(DetailSide.down, lowerSide);
    }

    public BallSide getBallSide(DetailSide side) {
        return sides.get(side);
    }

    public int getId() {
        return id;
    }

    @NotNull
    public List<Integer> getAllowedRotations() {
        return allowedRotations;
    }

    public void setAllowedRotations(@NotNull List<Integer> allowedRotations) {
        this.allowedRotations = allowedRotations;
    }

    public int getDetailDiversity() {
        return sides.keySet().size() + new HashSet<>(sides.values()).size() + allowedRotations.size();
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", sides=" + sides +
                ", allowed rotations=" + allowedRotations +
                '}';
    }
}
