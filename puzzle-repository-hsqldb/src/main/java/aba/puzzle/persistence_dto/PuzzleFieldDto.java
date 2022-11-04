package aba.puzzle.persistence_dto;

import javax.persistence.*;

@Entity
@Table(name = "PUZZLE_FIELD")
public class PuzzleFieldDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "SHIFT_X")
    private Integer shiftX;

    @Column(name = "SHIFT_Y")
    private Integer shiftY;

    @ManyToOne
    @JoinColumn(name = "PUZZLE_CONFIG_ID")
    private PuzzleConfigDto puzzleConfigDto;

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

    public PuzzleConfigDto getPuzzleConfigDto() {
        return puzzleConfigDto;
    }

    public void setPuzzleConfigDto(PuzzleConfigDto puzzleConfigDto) {
        this.puzzleConfigDto = puzzleConfigDto;
    }

    public Integer getId() {
        return id;
    }
}
