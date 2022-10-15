package aba.puzzle.persistence_vo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="TASK_DETAILS")
public class TaskDetailsVO {
    @OneToOne
    @JoinColumn(name="TASK_ID")
    private String taskId;

    @OneToOne
    @JoinColumn(name="COLOR_LEFT_SIDE")
    private String colorLeftSide;

    @OneToOne
    @JoinColumn(name="PART_LEFT_SIDE")
    private String partLeftSide;

    @OneToOne
    @JoinColumn(name="COLOR_UPPER_SIDE")
    private String colorUpperSide;

    @OneToOne
    @JoinColumn(name="PART_UPPER_SIDE")
    private String partUpperSide;

    @OneToOne
    @JoinColumn(name="COLOR_RIGHT_SIDE")
    private String colorRightSide;

    @OneToOne
    @JoinColumn(name="PART_RIGHT_SIDE")
    private String partRightSide;

    @OneToOne
    @JoinColumn(name="COLOR_LOWER_SIDE")
    private String colorLowerSide;

    @OneToOne
    @JoinColumn(name="PART_LOWER_SIDE")
    private String partLowerSide;
}
