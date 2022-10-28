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
        val vo = PuzzleConfigDto.fromPuzzleConfig(pc)

        val objectMapper = ObjectMapper()
        val serializedVO = objectMapper.writeValueAsString(vo)
        log.info { serializedVO }
        assertEquals("{\"puzzleMap\":{\"puzzleFields\":[{\"shiftX\":0,\"shiftY\":0},{\"shiftX\":0,\"shiftY\":1}]},\"puzzleDetails\":[{\"id\":0,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":1,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}}]}", serializedVO)
    }

    @Test
    fun testPuzzleConigSerialize2x2() {
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
        val vo = PuzzleConfigDto.fromPuzzleConfig(pc)

        val objectMapper = ObjectMapper()
        val serializedVO = objectMapper.writeValueAsString(vo)
        log.info { serializedVO }
        assertEquals(
            "{\"puzzleMap\":{\"puzzleFields\":[{\"shiftX\":0,\"shiftY\":0},{\"shiftX\":0,\"shiftY\":1},{\"shiftX\":1,\"shiftY\":0},{\"shiftX\":1,\"shiftY\":1}]},\"puzzleDetails\":[{\"id\":0,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":1,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":2,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}},{\"id\":3,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}}]}",
            serializedVO
        )
    }
    @Test
    fun testPuzzleConigSerialize3x3() {
        val pc = PuzzleConfig(
            puzzleMap = PuzzleMap(
                puzzleFields = listOf(
                    PuzzleField(0, 0),
                    PuzzleField(0, 1),
                    PuzzleField(0, 2),
                    PuzzleField(1, 0),
                    PuzzleField(1, 1),
                    PuzzleField(1, 2),
                    PuzzleField(2, 0),
                    PuzzleField(2, 1),
                    PuzzleField(2, 2)
                )
            ),
            puzzleDetails = listOf(
                Detail(
                    0,
                    BallSide(Color.white, BallPart.two_thirds),
                    BallSide(Color.red, BallPart.one_third),
                    BallSide(Color.green, BallPart.one_third),
                    BallSide(Color.red, BallPart.one_third),
                ),
                Detail(
                    1,
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.red, BallPart.two_thirds),
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.one_third),
                ),
                Detail(
                    2,
                    BallSide(Color.white, BallPart.two_thirds),
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.yellow, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.two_thirds),
                ),
                Detail(
                    3,
                    BallSide(Color.green, BallPart.one_third),
                    BallSide(Color.red, BallPart.two_thirds),
                    BallSide(Color.white, BallPart.two_thirds),
                    BallSide(Color.yellow, BallPart.two_thirds),
                ),
                Detail(
                    4,
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.two_thirds),
                    BallSide(Color.white, BallPart.two_thirds),
                    BallSide(Color.yellow, BallPart.two_thirds),
                ),
                Detail(
                    5,
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.one_third),
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.red, BallPart.two_thirds),
                ),
                Detail(
                    6,
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.yellow, BallPart.one_third),
                    BallSide(Color.white, BallPart.two_thirds),
                    BallSide(Color.red, BallPart.two_thirds),
                ),
                Detail(
                    7,
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.one_third),
                    BallSide(Color.green, BallPart.one_third),
                    BallSide(Color.yellow, BallPart.one_third),
                ),
                Detail(
                    8,
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.red, BallPart.one_third),
                    BallSide(Color.white, BallPart.one_third),
                    BallSide(Color.red, BallPart.two_thirds),
                )
            )
        )
        val vo = PuzzleConfigDto.fromPuzzleConfig(pc)

        val objectMapper = ObjectMapper()
        val serializedVO = objectMapper.writeValueAsString(vo)
        log.info { serializedVO }
        assertEquals(
            "{\"puzzleMap\":{\"puzzleFields\":[{\"shiftX\":0,\"shiftY\":0},{\"shiftX\":0,\"shiftY\":1},{\"shiftX\":0,\"shiftY\":2},{\"shiftX\":1,\"shiftY\":0},{\"shiftX\":1,\"shiftY\":1},{\"shiftX\":1,\"shiftY\":2},{\"shiftX\":2,\"shiftY\":0},{\"shiftX\":2,\"shiftY\":1},{\"shiftX\":2,\"shiftY\":2}]},\"puzzleDetails\":[{\"id\":0,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":1,\"left\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":2,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}},{\"id\":3,\"left\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}},{\"id\":4,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}},{\"id\":5,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}}},{\"id\":6,\"left\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}}},{\"id\":7,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":8,\"left\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}}}]}",
            serializedVO
        )
    }

    @Test
    fun testPuzzleConigDeserialize(){
        val serializedVO = "{\"puzzleMap\":{\"puzzleFields\":[{\"shiftX\":0,\"shiftY\":0},{\"shiftX\":0,\"shiftY\":1}]},\"puzzleDetails\":[{\"id\":0,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"one_third\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"one_third\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"one_third\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"one_third\"}}},{\"id\":1,\"left\":{\"color\":{\"color\":\"white\"},\"side\":{\"side\":\"two_thirds\"}},\"upper\":{\"color\":{\"color\":\"red\"},\"side\":{\"side\":\"two_thirds\"}},\"right\":{\"color\":{\"color\":\"green\"},\"side\":{\"side\":\"two_thirds\"}},\"lower\":{\"color\":{\"color\":\"yellow\"},\"side\":{\"side\":\"two_thirds\"}}}]}"
        val objectMapper = ObjectMapper()
        val vo = objectMapper.readValue(StringReader(serializedVO), PuzzleConfigDto::class.java)
        assertEquals(2, vo.puzzleDetails.size)
    }
}