package aba.puzzle.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class DetailWithRotation {
    private Detail detail;
    private int rotation;

    public DetailWithRotation(Detail detail, int rotation) {
        this.detail = detail;
        this.rotation = rotation;
    }

    public BallSide getBallSide(DetailSide position) {
        DetailSide detailSide = DetailSide.getRotatedDetailSide(position, rotation);
        return detail.getBallSide(detailSide);
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "DetailWithRotation{" +
                "detail=" + detail +
                ", rotation=" + rotation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailWithRotation that = (DetailWithRotation) o;
        return Arrays.stream(DetailSide.values()).allMatch(detailSide ->
                this.getBallSide(detailSide).equals(that.getBallSide(detailSide)));
    }

    @Override
    public int hashCode() {
        return Arrays.stream(DetailSide.values()).map(detailSide ->
                Objects.hash(this.getBallSide(detailSide))).mapToInt(v -> v).sum();
    }
}
