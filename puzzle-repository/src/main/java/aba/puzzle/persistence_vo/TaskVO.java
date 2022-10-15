package aba.puzzle.persistence_vo;

import javax.persistence.*;

@Entity(name="TASK")
public class TaskVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="EXT_TASK_ID")
    private String extTaskId;

    @OneToOne
    @JoinColumn(name="START_TIME")
    private java.sql.Timestamp startTime;

}
