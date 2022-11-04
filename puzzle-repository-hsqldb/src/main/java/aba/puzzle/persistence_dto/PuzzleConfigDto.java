package aba.puzzle.persistence_dto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "PUZZLE_CONFIG")
public class PuzzleConfigDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EXT_PUZZLE_CONFIG_ID")
    private String extPuzzleConfigId;

    @Column(name = "START_TIME")
    private java.sql.Timestamp startTime;

    @OneToMany(mappedBy = "puzzleConfigDto",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PuzzleDetailDto> puzzleDetailDtos;

    @OneToMany(mappedBy = "puzzleConfigDto",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PuzzleFieldDto> puzzleFieldDtos;


    public Integer getId() {
        return id;
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

    public Set<PuzzleDetailDto> getPuzzleDetailDtos() {
        return puzzleDetailDtos;
    }

    public void setPuzzleDetailDtos(Set<PuzzleDetailDto> puzzleDetailDtos) {
        this.puzzleDetailDtos = puzzleDetailDtos;
    }

    public Set<PuzzleFieldDto> getPuzzleFieldDtos() {
        return puzzleFieldDtos;
    }

    public void setPuzzleFieldDtos(Set<PuzzleFieldDto> puzzleFieldDtos) {
        this.puzzleFieldDtos = puzzleFieldDtos;
    }

}
