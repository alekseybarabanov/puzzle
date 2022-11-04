package aba.puzzle.persistence_dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "PUZZLE_STATE")
public class PuzzleStateDto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "puzzleStateDto",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PuzzleStateFieldDto> puzzleStateFieldDtos;

    @Column(name = "PUZZLE_CONFIG_ID")
    private Integer puzzleConfigId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<PuzzleStateFieldDto> getPuzzleStateFieldDtos() {
        return puzzleStateFieldDtos;
    }

    public void setPuzzleStateFieldDtos(Set<PuzzleStateFieldDto> puzzleStateFieldDtos) {
        this.puzzleStateFieldDtos = puzzleStateFieldDtos;
    }

    public Integer getPuzzleConfigId() {
        return puzzleConfigId;
    }

    public void setPuzzleConfigId(Integer puzzleConfigId) {
        this.puzzleConfigId = puzzleConfigId;
    }
}
