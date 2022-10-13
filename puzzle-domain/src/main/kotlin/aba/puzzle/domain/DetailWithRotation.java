package aba.puzzle.domain;

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
}
