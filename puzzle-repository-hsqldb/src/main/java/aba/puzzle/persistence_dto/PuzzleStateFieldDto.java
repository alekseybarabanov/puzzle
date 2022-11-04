package aba.puzzle.persistence_dto;

import javax.persistence.*;

@Entity
@Table(name = "PUZZLE_STATE_FIELD")
public class PuzzleStateFieldDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PUZZLE_DETAIL_ID")
    private Integer puzzleDetailId;

    @Column(name = "PUZZLE_FIELD_ID")
    private Integer puzzleFieldId;

    @ManyToOne
    @JoinColumn(name = "PUZZLE_STATE_ID")
    private PuzzleStateDto puzzleStateDto;

    @Column(name = "ROTATION")
    private Integer rotation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPuzzleDetailId() {
        return puzzleDetailId;
    }

    public void setPuzzleDetailId(Integer puzzleDetailId) {
        this.puzzleDetailId = puzzleDetailId;
    }

    public Integer getPuzzleFieldId() {
        return puzzleFieldId;
    }

    public void setPuzzleFieldId(Integer puzzleFieldId) {
        this.puzzleFieldId = puzzleFieldId;
    }

    public PuzzleStateDto getPuzzleStateDto() {
        return puzzleStateDto;
    }

    public void setPuzzleStateDto(PuzzleStateDto puzzleStateDto) {
        this.puzzleStateDto = puzzleStateDto;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }
}
