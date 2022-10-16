package aba.puzzle.persistence_vo;

import aba.puzzle.domain.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "PUZZLE_CONFIG")
public class PuzzleConfigVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EXT_PUZZLE_CONFIG_ID")
    private String extPuzzleConfigId;

    @Column(name = "START_TIME")
    private java.sql.Timestamp startTime;

    @OneToMany(mappedBy = "puzzleConfigVO")
    private Set<PuzzleDetailVO> puzzleDetailVOS;

    @OneToMany(mappedBy = "puzzleConfigVO")
    private Set<PuzzleFieldVO> puzzleFieldVOS;

    public static PuzzleConfigVO fromPuzzleConfig(PuzzleConfig config, String taskId) {
        PuzzleConfigVO puzzleConfigVO = new PuzzleConfigVO();
        puzzleConfigVO.setExtPuzzleConfigId(taskId);
        puzzleConfigVO.setStartTime(Timestamp.from(ZonedDateTime.now().toInstant()));
        puzzleConfigVO.setPuzzleFieldVOS(config.getPuzzleMap().getPuzzleFields().stream().map(puzzleField -> {
            PuzzleFieldVO puzzleFieldVO = new PuzzleFieldVO();
            puzzleFieldVO.setShiftX(puzzleField.getShiftX());
            puzzleFieldVO.setShiftY(puzzleField.getShiftY());
            return puzzleFieldVO;
        }).collect(Collectors.toSet()));
        puzzleConfigVO.setPuzzleDetailVOS(config.getPuzzleDetails().stream().map(puzzleDetail -> {
            PuzzleDetailVO puzzleDetailVO = new PuzzleDetailVO();
            BallSide leftBallSide = puzzleDetail.getBallSide(DetailSide.left);
            puzzleDetailVO.setColorLeftSide(leftBallSide.getColor().name());
            puzzleDetailVO.setPartLeftSide(leftBallSide.getBallPart().name());

            BallSide upBallSide = puzzleDetail.getBallSide(DetailSide.up);
            puzzleDetailVO.setColorUpperSide(upBallSide.getColor().name());
            puzzleDetailVO.setPartUpperSide(upBallSide.getBallPart().name());

            BallSide rightBallSide = puzzleDetail.getBallSide(DetailSide.right);
            puzzleDetailVO.setColorRightSide(rightBallSide.getColor().name());
            puzzleDetailVO.setPartRightSide(rightBallSide.getBallPart().name());

            BallSide downBallSide = puzzleDetail.getBallSide(DetailSide.down);
            puzzleDetailVO.setColorLowerSide(downBallSide.getColor().name());
            puzzleDetailVO.setPartLowerSide(downBallSide.getBallPart().name());
            return puzzleDetailVO;
        }).collect(Collectors.toSet()));
        return puzzleConfigVO;
    }

    public static PuzzleConfig toPuzzleConfig(PuzzleConfigVO puzzleConfigVO) {
        return new PuzzleConfig(
                new PuzzleMap(
                        puzzleConfigVO.getPuzzleFieldVOS().stream().map(puzzleFieldVO -> new PuzzleField(
                                puzzleFieldVO.getShiftX(),
                                puzzleFieldVO.getShiftY()
                        )).collect(Collectors.toList())),
                puzzleConfigVO.getPuzzleDetailVOS().stream().map(puzzleDetailVO -> new Detail(puzzleDetailVO.getId(),
                        new BallSide(Color.valueOf(puzzleDetailVO.getColorLeftSide()), BallPart.valueOf(puzzleDetailVO.getPartLeftSide())),
                        new BallSide(Color.valueOf(puzzleDetailVO.getColorUpperSide()), BallPart.valueOf(puzzleDetailVO.getPartUpperSide())),
                        new BallSide(Color.valueOf(puzzleDetailVO.getColorRightSide()), BallPart.valueOf(puzzleDetailVO.getPartRightSide())),
                        new BallSide(Color.valueOf(puzzleDetailVO.getColorLowerSide()), BallPart.valueOf(puzzleDetailVO.getPartLowerSide()))
                )).collect(Collectors.toSet()));
    }

    public String getExtPuzzleConfigId() {
        return extPuzzleConfigId;
    }

    public void setExtPuzzleConfigId(String extPuzzleConfigId) {
        this.extPuzzleConfigId = extPuzzleConfigId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Set<PuzzleDetailVO> getPuzzleDetailVOS() {
        return puzzleDetailVOS;
    }

    public void setPuzzleDetailVOS(Set<PuzzleDetailVO> puzzleDetailVOS) {
        this.puzzleDetailVOS = puzzleDetailVOS;
    }

    public Set<PuzzleFieldVO> getPuzzleFieldVOS() {
        return puzzleFieldVOS;
    }

    public void setPuzzleFieldVOS(Set<PuzzleFieldVO> puzzleFieldVOS) {
        this.puzzleFieldVOS = puzzleFieldVOS;
    }
}
