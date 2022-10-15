package aba.puzzle.persistence_vo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="TASK_PUZZLE_MAP")
public class TaskPuzzleMapVO {
    @OneToOne
    @JoinColumn(name="TASK_ID")
    private String taskId;

    @OneToOne
    @JoinColumn(name="SHIFT_X")
    private String shiftX;

    @OneToOne
    @JoinColumn(name="SHIFT_Y")
    private String shiftY;

}
