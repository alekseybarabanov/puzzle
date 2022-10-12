package aba.kover.core;

import aba.kover.core.detail.*;
import aba.kover.core.detail.*;
import aba.kover.core.detail.*;
import aba.kover.core.detail.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KoverStateServiceImpl implements KoverStateService {

    private static DetailMatcher detailMatcher = new DetailMatcher();

    public void launch() {

    }
    @Override
    @Nullable
    public List<KoverState> addDetail(@NotNull KoverState currentState, @NotNull Detail detail, @NotNull KoverPosition position) {
        List<SideWithPosition> dependentSides = getDependentSides(currentState, position);
        List<KoverState> result = new ArrayList<KoverState>();
        for (int rotation = 0; rotation < 4; rotation++) {
            DetailWithRotation detailWithRotation = new DetailWithRotation(detail, rotation);
            if (detailMatcher.isMatch(dependentSides, detailWithRotation)) {
                result.add(new KoverState(currentState, position, detailWithRotation));
            }
        }
        return result;
    }

    private List<SideWithPosition> getDependentSides(@NotNull KoverState currentState, KoverPosition position) {
        Map<KoverPosition, DetailSide> dependentPositions = KoverPosition.getDependentPositions(position);
        List<SideWithPosition> result = new ArrayList<SideWithPosition>();
        for (Map.Entry<KoverPosition, DetailSide> entry : dependentPositions.entrySet()) {
            DetailWithRotation detailWithRotation = currentState.getPositionedDetails().get(entry.getKey());
            if (detailWithRotation != null) {
                result.add(new SideWithPosition(detailWithRotation.getBallSide(entry.getValue()), entry.getKey(), entry.getValue()));
            }
        }
        return result;
    }
    private static class DetailMatcher {
        private BallSidesMatcher ballSidesMatcher = new BallSidesMatcher();

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

    private static class SideWithPosition {
        private BallSide ballSide;
        private KoverPosition positions;
        private DetailSide detailSide;

        private SideWithPosition(BallSide ballSide, KoverPosition position, DetailSide side) {
            this.ballSide = ballSide;
            this.positions = position;
            this.detailSide = side;
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

}
