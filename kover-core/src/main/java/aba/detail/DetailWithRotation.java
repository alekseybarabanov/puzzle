package aba.detail;

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
}
