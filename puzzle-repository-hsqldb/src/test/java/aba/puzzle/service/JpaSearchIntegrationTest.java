package aba.puzzle.service;


import aba.puzzle.domain.*;
import aba.puzzle.persistence_vo.PuzzleConfigVO;
import aba.puzzle.persistence_vo.PuzzleDetailVO;
import aba.puzzle.persistence_vo.PuzzleFieldVO;
import aba.puzzle.repository.PuzzleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class JpaSearchIntegrationTest {
    @Autowired
    private RepositoryService repositoryService;

    private PuzzleConfig puzzle1;

    @BeforeEach
    public void init() {
        List<PuzzleField> puzzleFields = new ArrayList<>(2);
        puzzleFields.add(new PuzzleField(0, 0));
        puzzleFields.add(new PuzzleField(0, 1));
        List<Detail> puzzleDetail = new ArrayList<>(2);
        puzzleDetail.add(new Detail(0,
                new BallSide(Color.green, BallPart.one_third),
                new BallSide(Color.yellow, BallPart.two_thirds),
                new BallSide(Color.green, BallPart.one_third),
                new BallSide(Color.green, BallPart.one_third)
        ));
        puzzleDetail.add(new Detail(1,
                new BallSide(Color.white, BallPart.one_third),
                new BallSide(Color.red, BallPart.two_thirds),
                new BallSide(Color.green, BallPart.one_third),
                new BallSide(Color.green, BallPart.one_third)
        ));
        puzzle1 = new PuzzleConfig(
                new PuzzleMap(puzzleFields),
                puzzleDetail
        );
        repositoryService.storePuzzleConfig(puzzle1, "test");
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        PuzzleConfig pc = repositoryService.getPuzzleConfig("test");

        assertEquals(2, pc.getPuzzleMap().getPuzzleFields().size());
        Iterator<PuzzleField> iterator = pc.getPuzzleMap().getPuzzleFields().iterator();
        PuzzleField p1 = iterator.next();
        PuzzleField p2 = iterator.next();
        assertEquals(0, p1.getShiftX() + p2.getShiftX());
        assertEquals(1, p1.getShiftY() + p2.getShiftY());

        assertEquals(2, pc.getPuzzleDetails().size());
    }

    @Configuration
    @ComponentScan(basePackages = "aba.puzzle")
    static class AppConfig {
    }
}
