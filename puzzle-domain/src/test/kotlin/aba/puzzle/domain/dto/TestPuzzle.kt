package aba.puzzle.domain.dto

import aba.puzzle.domain.*
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import java.io.StringReader
import kotlin.test.assertEquals

class TestPuzzle {
    private val log = KotlinLogging.logger {}

    @Test
    fun testPuzzleConigSerialize() {
        val pc = PuzzleConfig(
            puzzleMap = PuzzleMap(
                puzzleFields = listOf(
                    PuzzleField(0, 0),
                    PuzzleField(0, 1)
                )
            ),
            puzzleDetails = listOf(
                Detail(
                    0,
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.red, BallPart.one_third),
                    BallSide(Color.green, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.one_third),
                ),
                Detail(
                    1,
                    BallSide(Color.white, BallPart.two_thirds),
                    BallSide(Color.red, BallPart.two_thirds),
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.yellow, BallPart.two_thirds),
                )
            )
        )
        val vo = PuzzleConfigVO.fromPuzzleConfig(pc)

        val objectMapper = ObjectMapper()
        val serializedVO = objectMapper.writeValueAsString(vo)
        log.info { serializedVO }
        assertEquals("{\"puzzleMap\":{\"puzzleFields\":[{\"shiftX\":0,\"shiftY\":0},{\"shiftX\":0,\"shiftY\":1}]},\"puzzleDetails\":[{\"id\":0,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":1,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}}]}", serializedVO)
    }

    @Test
    fun testPuzzleConigSerialize2() {
        val pc = PuzzleConfig(
            puzzleMap = PuzzleMap(
                puzzleFields = listOf(
                    PuzzleField(0, 0),
                    PuzzleField(0, 1),
                    PuzzleField(1, 0),
                    PuzzleField(1, 1)
                )
            ),
            puzzleDetails = listOf(
                Detail(
                    0,
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.red, BallPart.one_third),
                    BallSide(Color.green, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.one_third),
                ),
                Detail(
                    1,
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.red, BallPart.two_thirds),
                    BallSide(Color.green, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.one_third),
                ),
                Detail(
                    2,
                    BallSide(Color.white, BallPart.two_thirds),
                    BallSide(Color.red, BallPart.two_thirds),
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.yellow, BallPart.two_thirds),
                ),
                Detail(
                    3,
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.red, BallPart.one_third),
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.yellow, BallPart.one_third),
                )
            )
        )
        val vo = PuzzleConfigVO.fromPuzzleConfig(pc)

        val objectMapper = ObjectMapper()
        val serializedVO = objectMapper.writeValueAsString(vo)
        log.info { serializedVO }
        assertEquals(
            "{\"puzzleMap\":{\"puzzleFields\":[{\"shiftX\":0,\"shiftY\":0},{\"shiftX\":0,\"shiftY\":1},{\"shiftX\":1,\"shiftY\":0},{\"shiftX\":1,\"shiftY\":1}]},\"puzzleDetails\":[{\"id\":0,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":1,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":2,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}},{\"id\":3,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}}]}",
            serializedVO
        )
    }

    @Test
    fun testPuzzleConigDeserialize(){
        val serializedVO = "{\"puzzleMap\":{\"puzzleFields\":[{\"shiftX\":0,\"shiftY\":0},{\"shiftX\":0,\"shiftY\":1}]},\"puzzleDetails\":[{\"id\":0,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":1,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}}]}"
        val objectMapper = ObjectMapper()
        val vo = objectMapper.readValue(StringReader(serializedVO), PuzzleConfigVO::class.java)
        assertEquals(2, vo.puzzleDetails.size)
    }
}