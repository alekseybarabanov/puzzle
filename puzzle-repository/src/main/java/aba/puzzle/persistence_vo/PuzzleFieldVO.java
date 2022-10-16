package aba.puzzle.persistence_vo;

import javax.persistence.*;

@Entity
@Table(name="PUZZLE_FIELD")
public class PuzzleFieldVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="SHIFT_X")
    private Integer shiftX;

    @Column(name="SHIFT_Y")
    private Integer shiftY;

    @ManyToOne
    @JoinColumn(name="PUZZLE_CONFIG_ID")
    private PuzzleConfigVO puzzleConfigVO;

    public Integer getShiftX() {
        return shiftX;
    }

    public void setShiftX(Integer shiftX) {
        this.shiftX = shiftX;
    }

    public Integer getShiftY() {
        return shiftY;
    }

    public void setShiftY(Integer shiftY) {
        this.shiftY = shiftY;
    }

    public PuzzleConfigVO getPuzzleConfigVO() {
        return puzzleConfigVO;
    }

    public void setPuzzleConfigVO(PuzzleConfigVO puzzleConfigVO) {
        this.puzzleConfigVO = puzzleConfigVO;
    }
}
