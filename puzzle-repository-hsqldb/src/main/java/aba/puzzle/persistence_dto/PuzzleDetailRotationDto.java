package aba.puzzle.persistence_dto;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ALLOWED_ROTATIONS")
public class PuzzleDetailRotationDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ROTATION")
    private Integer rotation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "PUZZLE_DETAIL_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PuzzleDetailDto puzzleDetailDto;

    public Integer getId() {
        return id;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }

    public PuzzleDetailDto getPuzzleDetailDto() {
        return puzzleDetailDto;
    }

    public void setPuzzleDetailDto(PuzzleDetailDto puzzleDetailDto) {
        this.puzzleDetailDto = puzzleDetailDto;
    }
}
