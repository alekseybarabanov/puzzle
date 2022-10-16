package aba.puzzle.service;


import aba.puzzle.domain.*;
import aba.puzzle.persistence_vo.PuzzleConfigVO;
import aba.puzzle.persistence_vo.PuzzleDetailVO;
import aba.puzzle.persistence_vo.PuzzleFieldVO;
import aba.puzzle.repository.PuzzleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaSearchIntegrationTest {
    @Autowired
    private PuzzleRepository repository;

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
        PuzzleConfigVO puzzleConfigVO = PuzzleConfigVO.fromPuzzleConfig(puzzle1, "test");
        repository.save(puzzleConfigVO);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        RepositoryServiceImpl.PuzzleConfigSpecification spec =
                new RepositoryServiceImpl.PuzzleConfigSpecification("test");

        List<PuzzleConfigVO> results = repository.findAll(spec);

        assertEquals(results.size(), 1);
        PuzzleConfigVO rs = results.get(0);
        assertEquals("test", rs.getExtPuzzleConfigId());
        assertEquals(2, rs.getPuzzleFieldVOS().size());
        Iterator<PuzzleFieldVO> iterator = rs.getPuzzleFieldVOS().iterator();
        PuzzleFieldVO p1 = iterator.next();
        PuzzleFieldVO p2 = iterator.next();
        assertEquals(0, p1.getShiftX() + p2.getShiftX());
        assertEquals(1, p1.getShiftY() + p2.getShiftY());

        assertEquals(2, rs.getPuzzleDetailVOS().size());
//        Iterator<PuzzleDetailVO> iteratorDetails = rs.getPuzzleDetailVOS().iterator();
//        PuzzleDetailVO d1 = iteratorDetails.next();
//        PuzzleDetailVO d2 = iteratorDetails.next();
//        HashMap<Integer, PuzzleDetailVO> detailsMap = new HashMap<>();
//        detailsMap.put(d1.getId(), d1);
//        detailsMap.put(d2.getId(), d2);
//        assertEquals(detailsMap.get(0).getColorLeftSide(), Color.green.name());
//        assertThat(userJohn, isIn(results));
    }
}
