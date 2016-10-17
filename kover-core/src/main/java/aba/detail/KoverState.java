package aba.detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alekseybarabanov on 13.08.16.
 */
public class KoverState {
    private Map<KoverPositions, DetailWithRotation> positionedDetails = new HashMap<KoverPositions, DetailWithRotation>();
    private static BallSidesMatcher ballSidesMatcher = new BallSidesMatcher();
    private static DetailMatcher detailMatcher = new DetailMatcher();

    public List<Integer> getMatchingRotations(KoverPositions position, Detail detail) {
        List<SideWithPosition> dependentSides = getDependentSides(position);
        List<Integer> result = new ArrayList<Integer>();
        for (int rotation = 0; rotation < 4; rotation++) {
            DetailWithRotation detailWithRotation = new DetailWithRotation(detail, rotation);
            if (detailMatcher.isMatch(dependentSides, detailWithRotation)) {
                result.add(rotation);
            }
        }
        return result;
    }

    private List<SideWithPosition> getDependentSides(KoverPositions position) {
        Map<KoverPositions, DetailSide> dependentPositions = KoverPositions.getDependentPositions(position);
        List<SideWithPosition> result = new ArrayList<SideWithPosition>();
        for (Map.Entry<KoverPositions, DetailSide> entry : dependentPositions.entrySet()) {
            DetailWithRotation detailWithRotation = positionedDetails.get(entry.getKey());
            if (detailWithRotation != null) {
                result.add(new SideWithPosition(detailWithRotation.getBallSide(entry.getValue()), entry.getKey(), entry.getValue()));
            }
        }
        return result;
    }

    private static class DetailMatcher {
        public boolean isMatch(List<SideWithPosition> restrictions, DetailWithRotation detailWithRotation) {
            for (SideWithPosition sideWithPosition : restrictions) {
                BallSide detailWithRotationBallSide = detailWithRotation.getBallSide(DetailSide.getMatchingSide(sideWithPosition.detailSide));
                if (!ballSidesMatcher.isMatch(sideWithPosition.ballSide, detailWithRotationBallSide)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class BallSidesMatcher {
        public boolean isMatch(BallSide side1, BallSide side2) {
            if (side1.getColor() == side2.getColor() && BallPart.isFull(side1.getBallPart(), side2.getBallPart())) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static class SideWithPosition {
        private BallSide ballSide;
        private KoverPositions positions;
        private DetailSide detailSide;

        private SideWithPosition(BallSide ballSide, KoverPositions position, DetailSide side) {
            this.ballSide = ballSide;
            this.positions = position;
            this.detailSide = side;
        }
    }
}
