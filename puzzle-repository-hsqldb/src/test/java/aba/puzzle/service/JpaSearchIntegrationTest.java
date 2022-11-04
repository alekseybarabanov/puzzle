package aba.puzzle.service;


import aba.puzzle.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaSearchIntegrationTest {
    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testSearchConfig() {
        PuzzleConfig initialPuzzleConfig = createPuzzleConfig();
        repositoryService.storePuzzleConfig(initialPuzzleConfig);
        PuzzleConfig pc = repositoryService.getPuzzleConfig(initialPuzzleConfig.getExtId());

        assertEquals(2, pc.getPuzzleMap().getPuzzleFields().size());
        Iterator<PuzzleField> iterator = pc.getPuzzleMap().getPuzzleFields().iterator();
        PuzzleField p1 = iterator.next();
        PuzzleField p2 = iterator.next();
        assertEquals(0, p1.getShiftX() + p2.getShiftX());
        assertEquals(1, p1.getShiftY() + p2.getShiftY());
        assertEquals(2, pc.getPuzzleDetails().size());
        pc.getPuzzleDetails().forEach(detail -> {
            if (detail.getExtId() == 1) {
                assertEquals(3, detail.getAllowedRotations().size());
                assertTrue(detail.getAllowedRotations().contains(0));
                assertTrue(detail.getAllowedRotations().contains(1));
                assertTrue(detail.getAllowedRotations().contains(2));
            }
        });
    }

    public PuzzleConfig createPuzzleConfig() {
        List<PuzzleField> puzzleFields = new ArrayList<>(2);
        puzzleFields.add(new PuzzleField(null, 0, 0));
        puzzleFields.add(new PuzzleField(null, 0, 1));
        List<Detail> puzzleDetail = new ArrayList<>(2);
        puzzleDetail.add(new Detail(null, 0,
                new BallSide(Color.green, BallPart.one_third),
                new BallSide(Color.yellow, BallPart.two_thirds),
                new BallSide(Color.green, BallPart.one_third),
                new BallSide(Color.green, BallPart.one_third)
        ));
        Detail detail = new Detail(null, 1,
                new BallSide(Color.white, BallPart.one_third),
                new BallSide(Color.red, BallPart.two_thirds),
                new BallSide(Color.green, BallPart.one_third),
                new BallSide(Color.green, BallPart.one_third)
        );
        detail.setAllowedRotations(Arrays.asList(0, 1, 2));
        puzzleDetail.add(detail);
        return new PuzzleConfig(
                null,
                "test",
                new PuzzleMap(puzzleFields),
                puzzleDetail
        );
    }


    @Configuration
    @ComponentScan(basePackages = "aba.puzzle")
    static class AppConfig {
    }
}
