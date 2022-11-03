package aba.puzzle.persistence_vo;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="PUZZLE_DETAIL")
public class PuzzleDetailVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="EXT_ID")
    private Integer extId;

    @Column(name="COLOR_LEFT_SIDE")
    private String colorLeftSide;

    @Column(name="PART_LEFT_SIDE")
    private String partLeftSide;

    @Column(name="COLOR_UPPER_SIDE")
    private String colorUpperSide;

    @Column(name="PART_UPPER_SIDE")
    private String partUpperSide;

    @Column(name="COLOR_RIGHT_SIDE")
    private String colorRightSide;

    @Column(name="PART_RIGHT_SIDE")
    private String partRightSide;

    @Column(name="COLOR_LOWER_SIDE")
    private String colorLowerSide;

    @Column(name="PART_LOWER_SIDE")
    private String partLowerSide;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="PUZZLE_CONFIG_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PuzzleConfigVO puzzleConfigVO;

    public String getColorLeftSide() {
        return colorLeftSide;
    }

    public void setColorLeftSide(String colorLeftSide) {
        this.colorLeftSide = colorLeftSide;
    }

    public String getPartLeftSide() {
        return partLeftSide;
    }

    public void setPartLeftSide(String partLeftSide) {
        this.partLeftSide = partLeftSide;
    }

    public String getColorUpperSide() {
        return colorUpperSide;
    }

    public void setColorUpperSide(String colorUpperSide) {
        this.colorUpperSide = colorUpperSide;
    }

    public String getPartUpperSide() {
        return partUpperSide;
    }

    public void setPartUpperSide(String partUpperSide) {
        this.partUpperSide = partUpperSide;
    }

    public String getColorRightSide() {
        return colorRightSide;
    }

    public void setColorRightSide(String colorRightSide) {
        this.colorRightSide = colorRightSide;
    }

    public String getPartRightSide() {
        return partRightSide;
    }

    public void setPartRightSide(String partRightSide) {
        this.partRightSide = partRightSide;
    }

    public String getColorLowerSide() {
        return colorLowerSide;
    }

    public void setColorLowerSide(String colorLowerSide) {
        this.colorLowerSide = colorLowerSide;
    }

    public String getPartLowerSide() {
        return partLowerSide;
    }

    public void setPartLowerSide(String partLowerSide) {
        this.partLowerSide = partLowerSide;
    }

    public PuzzleConfigVO getPuzzleConfigVO() {
        return puzzleConfigVO;
    }

    public void setPuzzleConfigVO(PuzzleConfigVO puzzleConfigVO) {
        this.puzzleConfigVO = puzzleConfigVO;
    }

    public Integer getId() {
        return id;
    }

    public Integer getExtId() {
        return extId;
    }

    public void setExtId(Integer extId) {
        this.extId = extId;
    }
}
