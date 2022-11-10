package aba.puzzle.domain.dto;

import aba.puzzle.domain.*;
import aba.puzzle.domain.rest.mapstruct.dto.*;
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {

    private MapStructMapper mapper = Mappers.getMapper(MapStructMapper.class);

    @Test
    public void testColorToColorDto() {
        ColorDto colorDto = mapper.colorToColorDto(Color.green);
        assertEquals("green", colorDto.getColor());
    }

    @Test
    public void testColorDtoToColor() {
        ColorDto colorDto = new ColorDto();
        colorDto.setColor("green");
        Color color = mapper.colorDtoToColor(colorDto);
        assertEquals(Color.green, color);
    }

    @Test
    public void testBallPartToBallPartDto() {
        BallPartDto dto = mapper.ballPartToBallPartDto(BallPart.two_thirds);
        assertEquals("two_thirds", dto.getSide());
    }

    @Test
    public void testBallPartDtoToBallPart() {
        BallPartDto ballPartDto = new BallPartDto();
        ballPartDto.setSide("one_third");
        BallPart ballPart = mapper.ballPartDtoToBallPart(ballPartDto);
        assertEquals("one_third", ballPart.name());
    }

    @Test
    public void testBallSideToBallSideDto() {
        BallSide ballSide = new BallSide(Color.red, BallPart.one_third);
        BallSideDto ballSideDto = mapper.ballSideToBallSideDto(ballSide);
        assertEquals("red", ballSideDto.getColor().getColor());
        assertEquals("one_third", ballSideDto.getSide().getSide());
    }

    @Test
    public void testBallSideDtoToBallSide() {
        BallSideDto ballSideDto = getBallSideDto("yellow", "one_third");
        BallSide ballSide = mapper.ballSideDtoToBallSide(ballSideDto);
        assertEquals("yellow", ballSide.getColor().name());
        assertEquals("one_third", ballSide.getBallPart().name());
    }

    @Test
    public void testDetailToPuzzleDetailDto() {
        Detail detail = getDetail(null, 2, Color.white, BallPart.two_thirds,
                Color.green, BallPart.two_thirds,
                Color.red, BallPart.one_third,
                Color.yellow, BallPart.two_thirds);
        detail.setAllowedRotations(Arrays.asList(0, 1, 2, 3));
        PuzzleDetailDto puzzleDetailDto = mapper.detailToPuzzleDetailDto(detail);
        assertEquals("white", puzzleDetailDto.getLeft().getColor().getColor());
        assertEquals("two_thirds", puzzleDetailDto.getLeft().getSide().getSide());
        assertEquals("green", puzzleDetailDto.getUpper().getColor().getColor());
        assertEquals("two_thirds", puzzleDetailDto.getUpper().getSide().getSide());
        assertEquals("red", puzzleDetailDto.getRight().getColor().getColor());
        assertEquals("one_third", puzzleDetailDto.getRight().getSide().getSide());
        assertEquals("yellow", puzzleDetailDto.getLower().getColor().getColor());
        assertEquals("two_thirds", puzzleDetailDto.getLower().getSide().getSide());
        assertEquals(2, puzzleDetailDto.getExtId());
        assertEquals(4, puzzleDetailDto.getAllowedRotations().size());
    }

    @Test
    public void testPuzzleDetailDtoToDetail() {
        PuzzleDetailDto puzzleDetailDto = getPuzzleDetailDto(3,
                "red", "two_thirds",
                "yellow", "two_thirds",
                "green", "one_third",
                "white", "two_thirds");
        puzzleDetailDto.setAllowedRotations(Arrays.asList(1, 2));
        Detail detail = mapper.puzzleDetailDtoToDetail(puzzleDetailDto);
        assertEquals(3, detail.getId());
        assertEquals("red", detail.getBallSide(DetailSide.left).getColor().name());
        assertEquals("two_thirds", detail.getBallSide(DetailSide.left).getBallPart().name());
        assertEquals("yellow", detail.getBallSide(DetailSide.up).getColor().name());
        assertEquals("two_thirds", detail.getBallSide(DetailSide.up).getBallPart().name());
        assertEquals("green", detail.getBallSide(DetailSide.right).getColor().name());
        assertEquals("one_third", detail.getBallSide(DetailSide.right).getBallPart().name());
        assertEquals("white", detail.getBallSide(DetailSide.down).getColor().name());
        assertEquals("two_thirds", detail.getBallSide(DetailSide.down).getBallPart().name());
        assertEquals(2, detail.getAllowedRotations().size());
    }

    @Test
    public void testDetailWithRotationToPuzzleDetailWithRotationDto() {
        DetailWithRotation detail = getDetailWithRotation(1, null, 2,
                Color.white, BallPart.two_thirds,
                Color.green, BallPart.two_thirds,
                Color.red, BallPart.one_third,
                Color.yellow, BallPart.two_thirds);
        PuzzleDetailWithRotationDto puzzleDetailWithRotationDto = mapper.detailWithRotationToPuzzleDetailWithRotationDto(detail);
        PuzzleDetailDto puzzleDetailDto = puzzleDetailWithRotationDto.getDetail();
        assertEquals(1, puzzleDetailWithRotationDto.getRotation());
        assertEquals("white", puzzleDetailDto.getLeft().getColor().getColor());
        assertEquals("two_thirds", puzzleDetailDto.getLeft().getSide().getSide());
        assertEquals("green", puzzleDetailDto.getUpper().getColor().getColor());
        assertEquals("two_thirds", puzzleDetailDto.getUpper().getSide().getSide());
        assertEquals("red", puzzleDetailDto.getRight().getColor().getColor());
        assertEquals("one_third", puzzleDetailDto.getRight().getSide().getSide());
        assertEquals("yellow", puzzleDetailDto.getLower().getColor().getColor());
        assertEquals("two_thirds", puzzleDetailDto.getLower().getSide().getSide());
        assertEquals(2, puzzleDetailDto.getExtId());
    }

    @Test
    public void testPuzzleDetailWithRotationDtoToDetailWithRotation() {
        PuzzleDetailWithRotationDto puzzleDetailDto = getPuzzleDetailWithRotationDto(2, 3,
                "red", "two_thirds",
                "yellow", "two_thirds",
                "green", "one_third",
                "white", "two_thirds");
        DetailWithRotation detailWithRotation = mapper.puzzleDetailWithRotationDtoToDetailWithRotation(puzzleDetailDto);
        Detail detail = detailWithRotation.getDetail();
        assertEquals(2, detailWithRotation.getRotation());
        assertEquals(3, detail.getId());
        assertEquals("red", detail.getBallSide(DetailSide.left).getColor().name());
        assertEquals("two_thirds", detail.getBallSide(DetailSide.left).getBallPart().name());
        assertEquals("yellow", detail.getBallSide(DetailSide.up).getColor().name());
        assertEquals("two_thirds", detail.getBallSide(DetailSide.up).getBallPart().name());
        assertEquals("green", detail.getBallSide(DetailSide.right).getColor().name());
        assertEquals("one_third", detail.getBallSide(DetailSide.right).getBallPart().name());
        assertEquals("white", detail.getBallSide(DetailSide.down).getColor().name());
        assertEquals("two_thirds", detail.getBallSide(DetailSide.down).getBallPart().name());
    }

    @Test
    public void testPuzzleMapToPuzzleMapDto() {
        PuzzleMap puzzleMap = new PuzzleMap(Arrays.asList(
                new PuzzleField(null, 0, 0),
                new PuzzleField(null, 0, 1),
                new PuzzleField(2, 1, 0)));
        PuzzleMapDto puzzleMapDto = mapper.puzzleMapToPuzzleMapDto(puzzleMap);
        assertEquals(0, puzzleMapDto.getPuzzleFields().get(0).getShiftX());
        assertEquals(0, puzzleMapDto.getPuzzleFields().get(0).getShiftY());
        assertEquals(0, puzzleMapDto.getPuzzleFields().get(1).getShiftX());
        assertEquals(1, puzzleMapDto.getPuzzleFields().get(1).getShiftY());
        assertEquals(1, puzzleMapDto.getPuzzleFields().get(2).getShiftX());
        assertEquals(0, puzzleMapDto.getPuzzleFields().get(2).getShiftY());
    }

    @Test
    public void testPuzzleMapDtoToPuzzleMap() {
        PuzzleMapDto puzzleMapDto = new PuzzleMapDto();
        puzzleMapDto.getPuzzleFields().addAll(Arrays.asList(
                new PuzzleFieldDto(null, 0, 0),
                new PuzzleFieldDto(null, 0, 1),
                new PuzzleFieldDto(2, 1, 0)
        ));
        PuzzleMap puzzleMap = mapper.puzzleMapDtoToPuzzleMap(puzzleMapDto);
        assertEquals(0, puzzleMap.getPuzzleFields().get(0).getShiftX());
        assertEquals(0, puzzleMap.getPuzzleFields().get(0).getShiftY());
        assertEquals(0, puzzleMap.getPuzzleFields().get(1).getShiftX());
        assertEquals(1, puzzleMap.getPuzzleFields().get(1).getShiftY());
        assertEquals(1, puzzleMap.getPuzzleFields().get(2).getShiftX());
        assertEquals(0, puzzleMap.getPuzzleFields().get(2).getShiftY());
    }

    @Test
    public void testPuzzleConfigDtoToPuzzleConfig() {
        PuzzleMapDto puzzleMapDto = new PuzzleMapDto();
        puzzleMapDto.getPuzzleFields().addAll(Arrays.asList(
                new PuzzleFieldDto(null, 0, 0),
                new PuzzleFieldDto(null, 0, 1),
                new PuzzleFieldDto(2, 1, 0)
        ));
        PuzzleConfigDto puzzleConfigDto = new PuzzleConfigDto(0, "test",
                puzzleMapDto,
                Arrays.asList(
                        getPuzzleDetailDto(0,
                                "red", "two_thirds",
                                "yellow", "two_thirds",
                                "green", "one_third",
                                "white", "two_thirds"),
                        getPuzzleDetailDto(1,
                                "yellow", "two_thirds",
                                "green", "two_thirds",
                                "green", "one_third",
                                "red", "two_thirds"),
                        getPuzzleDetailDto(2,
                                "white", "one_third",
                                "yellow", "two_thirds",
                                "red", "one_third",
                                "white", "one_third")
                )
        );
        PuzzleConfig puzzleConfig = mapper.puzzleConfigDtoToPuzzleConfig(puzzleConfigDto);
        assertEquals(0, puzzleConfig.getPuzzleMap().getPuzzleFields().get(0).getShiftX());
        assertEquals(0, puzzleConfig.getPuzzleMap().getPuzzleFields().get(0).getShiftY());
        assertEquals(0, puzzleConfig.getPuzzleMap().getPuzzleFields().get(1).getShiftX());
        assertEquals(1, puzzleConfig.getPuzzleMap().getPuzzleFields().get(1).getShiftY());
        assertEquals(1, puzzleConfig.getPuzzleMap().getPuzzleFields().get(2).getShiftX());
        assertEquals(0, puzzleConfig.getPuzzleMap().getPuzzleFields().get(2).getShiftY());
        Iterator<Detail> iterator = puzzleConfig.getPuzzleDetails().iterator();
        Detail detail1 = iterator.next();
        assertEquals("red", detail1.getBallSide(DetailSide.left).getColor().name());
        assertEquals("two_thirds", detail1.getBallSide(DetailSide.left).getBallPart().name());
        assertEquals("yellow", detail1.getBallSide(DetailSide.up).getColor().name());
        assertEquals("two_thirds", detail1.getBallSide(DetailSide.up).getBallPart().name());
        assertEquals("green", detail1.getBallSide(DetailSide.right).getColor().name());
        assertEquals("one_third", detail1.getBallSide(DetailSide.right).getBallPart().name());
        assertEquals("white", detail1.getBallSide(DetailSide.down).getColor().name());
        assertEquals("two_thirds", detail1.getBallSide(DetailSide.down).getBallPart().name());
        Detail detail2 = iterator.next();
        assertEquals("yellow", detail2.getBallSide(DetailSide.left).getColor().name());
        assertEquals("two_thirds", detail2.getBallSide(DetailSide.left).getBallPart().name());
        assertEquals("green", detail2.getBallSide(DetailSide.up).getColor().name());
        assertEquals("two_thirds", detail2.getBallSide(DetailSide.up).getBallPart().name());
        assertEquals("green", detail2.getBallSide(DetailSide.right).getColor().name());
        assertEquals("one_third", detail2.getBallSide(DetailSide.right).getBallPart().name());
        assertEquals("red", detail2.getBallSide(DetailSide.down).getColor().name());
        assertEquals("two_thirds", detail2.getBallSide(DetailSide.down).getBallPart().name());
        Detail detail3 = iterator.next();
        assertEquals("white", detail3.getBallSide(DetailSide.left).getColor().name());
        assertEquals("one_third", detail3.getBallSide(DetailSide.left).getBallPart().name());
        assertEquals("yellow", detail3.getBallSide(DetailSide.up).getColor().name());
        assertEquals("two_thirds", detail3.getBallSide(DetailSide.up).getBallPart().name());
        assertEquals("red", detail3.getBallSide(DetailSide.right).getColor().name());
        assertEquals("one_third", detail3.getBallSide(DetailSide.right).getBallPart().name());
        assertEquals("white", detail3.getBallSide(DetailSide.down).getColor().name());
        assertEquals("one_third", detail3.getBallSide(DetailSide.down).getBallPart().name());
    }

    @Test
    public void testPuzzleConfigToPuzzleConfigDto() {

        PuzzleConfig puzzleConfig = new PuzzleConfig(null, "test",
                new PuzzleMap(Arrays.asList(
                        new PuzzleField(null, 0, 0),
                        new PuzzleField(null, 0, 1),
                        new PuzzleField(1, 1, 0))),
                Arrays.asList(
                        getDetail(null, 0,
                                Color.white, BallPart.two_thirds,
                                Color.green, BallPart.two_thirds,
                                Color.red, BallPart.one_third,
                                Color.yellow, BallPart.two_thirds),
                        getDetail(null, 1,
                                Color.green, BallPart.two_thirds,
                                Color.yellow, BallPart.two_thirds,
                                Color.red, BallPart.one_third,
                                Color.red, BallPart.two_thirds),
                        getDetail(22, 2,
                                Color.white, BallPart.two_thirds,
                                Color.red, BallPart.two_thirds,
                                Color.white, BallPart.one_third,
                                Color.yellow, BallPart.two_thirds)
                )
        );
        PuzzleConfigDto puzzleConfigDto = mapper.puzzleConfigToPuzzleConfigDto(puzzleConfig);
        Iterator<PuzzleDetailDto> iterator = puzzleConfigDto.getPuzzleDetails().iterator();
        PuzzleDetailDto detail1 = iterator.next();
        assertEquals(0, detail1.getExtId());
        assertEquals("white", detail1.getLeft().getColor().getColor());
        assertEquals("two_thirds", detail1.getLeft().getSide().getSide());
        assertEquals("green", detail1.getUpper().getColor().getColor());
        assertEquals("two_thirds", detail1.getUpper().getSide().getSide());
        assertEquals("red", detail1.getRight().getColor().getColor());
        assertEquals("one_third", detail1.getRight().getSide().getSide());
        assertEquals("yellow", detail1.getLower().getColor().getColor());
        assertEquals("two_thirds", detail1.getLower().getSide().getSide());
        PuzzleDetailDto detail2 = iterator.next();
        assertEquals(1, detail2.getExtId());
        assertEquals("green", detail2.getLeft().getColor().getColor());
        assertEquals("two_thirds", detail2.getLeft().getSide().getSide());
        assertEquals("yellow", detail2.getUpper().getColor().getColor());
        assertEquals("two_thirds", detail2.getUpper().getSide().getSide());
        assertEquals("red", detail2.getRight().getColor().getColor());
        assertEquals("one_third", detail2.getRight().getSide().getSide());
        assertEquals("red", detail2.getLower().getColor().getColor());
        assertEquals("two_thirds", detail2.getLower().getSide().getSide());
        PuzzleDetailDto detail3 = iterator.next();
        assertEquals(22, detail3.getId());
        assertEquals(2, detail3.getExtId());
        assertEquals("white", detail3.getLeft().getColor().getColor());
        assertEquals("two_thirds", detail3.getLeft().getSide().getSide());
        assertEquals("red", detail3.getUpper().getColor().getColor());
        assertEquals("two_thirds", detail3.getUpper().getSide().getSide());
        assertEquals("white", detail3.getRight().getColor().getColor());
        assertEquals("one_third", detail3.getRight().getSide().getSide());
        assertEquals("yellow", detail3.getLower().getColor().getColor());
        assertEquals("two_thirds", detail3.getLower().getSide().getSide());
    }

    @Test
    public void testPuzzleStateToPuzzleStateDto() {
        PuzzleConfig puzzleConfig = new PuzzleConfig(null, "test",
                new PuzzleMap(Arrays.asList(
                        new PuzzleField(null, 0, 0),
                        new PuzzleField(null, 0, 1),
                        new PuzzleField(1, 1, 0))),
                Arrays.asList(
                        getDetail(null, 0,
                                Color.white, BallPart.two_thirds,
                                Color.green, BallPart.two_thirds,
                                Color.red, BallPart.one_third,
                                Color.yellow, BallPart.two_thirds),
                        getDetail(null, 1,
                                Color.green, BallPart.two_thirds,
                                Color.yellow, BallPart.two_thirds,
                                Color.red, BallPart.one_third,
                                Color.red, BallPart.two_thirds),
                        getDetail(22, 2,
                                Color.white, BallPart.two_thirds,
                                Color.red, BallPart.two_thirds,
                                Color.white, BallPart.one_third,
                                Color.yellow, BallPart.two_thirds)
                )
        );
        PuzzleState puzzleState = new PuzzleState(puzzleConfig);
        puzzleState.setPositionedDetails(IntStream.range(0, 3).mapToObj(i ->
                        new AbstractMap.SimpleEntry<PuzzleField, DetailWithRotation>(puzzleConfig.getPuzzleMap().getPuzzleFields().get(i),
                                new DetailWithRotation(((List<Detail>) puzzleConfig.getPuzzleDetails()).get(i), i)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        PuzzleStateDto puzzleStateDto = mapper.puzzleStateToPuzzleStateDto(puzzleState);
        Iterator<PuzzleDetailDto> iterator = puzzleStateDto.getPuzzleConfigDto().getPuzzleDetails().iterator();
        PuzzleDetailDto detail1 = iterator.next();
        assertEquals(0, detail1.getExtId());
        PuzzleDetailDto detail2 = iterator.next();
        assertEquals(1, detail2.getExtId());
        PuzzleDetailDto detail3 = iterator.next();
        assertEquals(2, detail3.getExtId());
        Iterator<PuzzleStateEntryDto> iterator2 = puzzleStateDto.getCoverage().iterator();
        PuzzleStateEntryDto sdetail1 = iterator2.next();
        assertEquals(sdetail1.getPuzzleDetailWithRotationDto().getRotation(), sdetail1.getPuzzleDetailWithRotationDto().getDetail().getExtId());
        PuzzleStateEntryDto sdetail2 = iterator2.next();
        assertEquals(sdetail2.getPuzzleDetailWithRotationDto().getRotation(), sdetail2.getPuzzleDetailWithRotationDto().getDetail().getExtId());
        PuzzleStateEntryDto sdetail3 = iterator2.next();
        assertEquals(sdetail3.getPuzzleDetailWithRotationDto().getRotation(), sdetail3.getPuzzleDetailWithRotationDto().getDetail().getExtId());
    }

    @NotNull
    private PuzzleDetailWithRotationDto getPuzzleDetailWithRotationDto(int rotation, int id,
                                                                       String color1, String ballPart1,
                                                                       String color2, String ballPart2,
                                                                       String color3, String ballPart3,
                                                                       String color4, String ballPart4) {
        PuzzleDetailWithRotationDto result = new PuzzleDetailWithRotationDto();
        result.setDetail(getPuzzleDetailDto(id,
                color1, ballPart1,
                color2, ballPart2,
                color3, ballPart3,
                color4, ballPart4));
        result.setRotation(rotation);
        return result;
    }

    private DetailWithRotation getDetailWithRotation(int rotation, Integer id, int extId,
                                                     Color color1, BallPart ballPart1,
                                                     Color color2, BallPart ballPart2,
                                                     Color color3, BallPart ballPart3,
                                                     Color color4, BallPart ballPart4) {

        return new DetailWithRotation(getDetail(id, extId,
                color1, ballPart1,
                color2, ballPart2,
                color3, ballPart3,
                color4, ballPart4), rotation);
    }

    @NotNull
    private PuzzleDetailDto getPuzzleDetailDto(int id,
                                               String color1, String ballPart1,
                                               String color2, String ballPart2,
                                               String color3, String ballPart3,
                                               String color4, String ballPart4) {
        PuzzleDetailDto puzzleDetailDto = new PuzzleDetailDto();
        puzzleDetailDto.setId(id);
        BallSideDto left = getBallSideDto(color1, ballPart1);
        BallSideDto upper = getBallSideDto(color2, ballPart2);
        BallSideDto right = getBallSideDto(color3, ballPart3);
        BallSideDto lower = getBallSideDto(color4, ballPart4);
        puzzleDetailDto.setLeft(left);
        puzzleDetailDto.setUpper(upper);
        puzzleDetailDto.setRight(right);
        puzzleDetailDto.setLower(lower);
        return puzzleDetailDto;
    }


    @NotNull
    private BallSideDto getBallSideDto(String color, String ballPart) {
        BallSideDto ballSideDto = new BallSideDto();
        BallPartDto ballPartDto = new BallPartDto();
        ballPartDto.setBallPart(ballPart);
        ballSideDto.setSide(ballPartDto);
        ColorDto colorDto = new ColorDto();
        colorDto.setColor(color);
        ballSideDto.setColor(colorDto);
        return ballSideDto;
    }


    @NotNull
    private Detail getDetail(Integer id, int extId,
                             Color color1, BallPart ballPart1,
                             Color color2, BallPart ballPart2,
                             Color color3, BallPart ballPart3,
                             Color color4, BallPart ballPart4) {
        BallSide ballSide1 = new BallSide(color1, ballPart1);
        BallSide ballSide2 = new BallSide(color2, ballPart2);
        BallSide ballSide3 = new BallSide(color3, ballPart3);
        BallSide ballSide4 = new BallSide(color4, ballPart4);
        Detail detail = new Detail(id, extId, ballSide1, ballSide2, ballSide3, ballSide4);
        return detail;
    }
}
