package aba.puzzle.domain;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class Detail {

    private Integer id = null;
    private int extId;
    private Map<DetailSide, BallSide> sides = new HashMap<DetailSide, BallSide>(4);

    @NotNull
    private List<Integer> allowedRotations = new ArrayList<>();

    public Detail(Integer id, int extId, BallSide leftSide, BallSide upperSide, BallSide rightSide, BallSide lowerSide) {
        this.id = id;
        this.extId = extId;
        sides.put(DetailSide.left, leftSide);
        sides.put(DetailSide.up, upperSide);
        sides.put(DetailSide.right, rightSide);
        sides.put(DetailSide.down, lowerSide);
    }

    private Detail(int id) {
        this.id = id;
    }

    public static Detail newDraftDetail(@NotNull int id) {
        return new Detail(id);
    }

    public boolean isDraft() {
        return sides.size() != 4;
    }

    public void setSide(DetailSide detailSide, BallSide ballSide) {
        sides.put(detailSide, ballSide);
    }

    public Collection<DetailSide> getFilledSides() {
        return sides.keySet();
    }

    public BallSide getBallSide(DetailSide side) {
        return sides.get(side);
    }

    public Integer getId() {
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
                "extId=" + extId +
                ", sides=" + sides +
                ", allowed rotations=" + allowedRotations +
                '}';
    }

    public int getExtId() {
        return extId;
    }

    public void setExtId(int extId) {
        this.extId = extId;
    }
}
