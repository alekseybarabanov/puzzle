package aba.puzzle.domain.rest.mapstruct.mapper;

import aba.puzzle.domain.*;
import aba.puzzle.domain.rest.mapstruct.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface MapStructMapper {

    MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);

    //// Wordings /////
    BallPartDto ballPartToBallPartDto(BallPart ballPart);

    default BallPart ballPartDtoToBallPart(BallPartDto ballPartDto) {
        switch (ballPartDto.getSide()) {
            case "one_third":
                return BallPart.one_third;
            case "two_thirds":
                return BallPart.two_thirds;
            default:
                throw new IllegalArgumentException("Cannot convert ball part " + ballPartDto.getSide());
        }
    }


    ColorDto colorToColorDto(Color color);

    default Color colorDtoToColor(ColorDto colorDto) {
        switch (colorDto.getColor()) {
            case "green":
                return Color.green;
            case "yellow":
                return Color.yellow;
            case "red":
                return Color.red;
            case "white":
                return Color.white;
            default:
                throw new IllegalArgumentException("Cannot convert color " + colorDto.getColor());
        }
    }

    ///// Detail ////
    @Mapping(source = "ballPart", target = "side")
    BallSideDto ballSideToBallSideDto(BallSide ballSide);

    @Mapping(source = "side", target = "ballPart")
    BallSide ballSideDtoToBallSide(BallSideDto ballSideDto);

    default PuzzleDetailDto detailToPuzzleDetailDto(Detail detail) {
        PuzzleDetailDto result = new PuzzleDetailDto();
        result.setId(detail.getId());
        for (DetailSide side : DetailSide.values()) {
            BallSide ballSide = detail.getBallSide(side);
            result.setSide(ballSideToBallSideDto(ballSide), side);
        }
        result.setAllowedRotations(new ArrayList<>(detail.getAllowedRotations()));
        return result;
    }

    default Detail puzzleDetailDtoToDetail(PuzzleDetailDto puzzleDetailDto) {
        Detail result = new Detail(puzzleDetailDto.getId(),
                puzzleDetailDto.getExtId(),
                ballSideDtoToBallSide(puzzleDetailDto.getLeft()),
                ballSideDtoToBallSide(puzzleDetailDto.getUpper()),
                ballSideDtoToBallSide(puzzleDetailDto.getRight()),
                ballSideDtoToBallSide(puzzleDetailDto.getLower())
        );
        if (puzzleDetailDto.getAllowedRotations() != null) {
            result.setAllowedRotations(puzzleDetailDto.getAllowedRotations());
        }
        return result;
    }

    // Detail with rotation
    PuzzleDetailWithRotationDto detailWithRotationToPuzzleDetailWithRotationDto(DetailWithRotation detailWithRotation);

    DetailWithRotation puzzleDetailWithRotationDtoToDetailWithRotation(PuzzleDetailWithRotationDto puzzleDetailWithRotationDto);

    // Puzzle State
    @Mapping(source = "shiftX", target = "shiftX")
    @Mapping(source = "shiftY", target = "shiftY")
    PuzzleFieldDto puzzleFieldToPuzzleFieldDto(PuzzleField puzzleField);

    PuzzleField puzzleFieldDtoToPuzzleField(PuzzleFieldDto puzzleFieldDto);

    default PuzzleStateEntryDto entryToPuzzleStateEntryDto(Map.Entry<PuzzleField, DetailWithRotation> entry) {
        final PuzzleStateEntryDto puzzleStateEntryDto = new PuzzleStateEntryDto();
        puzzleStateEntryDto.setPuzzleFieldDto(puzzleFieldToPuzzleFieldDto(entry.getKey()));
        puzzleStateEntryDto.setDetailWithRotationVO(detailWithRotationToPuzzleDetailWithRotationDto(entry.getValue()));
        return puzzleStateEntryDto;
    }

    default Map.Entry<PuzzleField, DetailWithRotation> puzzleStateEntryDtoToEntry(PuzzleStateEntryDto puzzleStateEntryDto) {
        Map<PuzzleField, DetailWithRotation> map = new HashMap<>();
        map.put(puzzleFieldDtoToPuzzleField(puzzleStateEntryDto.getPuzzleFieldDto()),
                puzzleDetailWithRotationDtoToDetailWithRotation(puzzleStateEntryDto.getDetailWithRotationDto()));
        return map.entrySet().iterator().next();
    }

    default PuzzleStateDto puzzleStateToPuzzleStateDto(PuzzleState puzzleState) {
        final PuzzleStateDto result = new PuzzleStateDto();
        for (Map.Entry<PuzzleField, DetailWithRotation> entry : puzzleState.getPositionedDetails().entrySet()) {
            result.getCoverage().add(entryToPuzzleStateEntryDto(entry));
        }
        return result;
    }

    default PuzzleState puzzleStateDtoToPuzzleState(PuzzleStateDto puzzleStateDto) {
        final PuzzleState puzzleState = new PuzzleState(puzzleConfigDtoToPuzzleConfig(puzzleStateDto.getPuzzleConfigDto()));
        final Map<PuzzleField, DetailWithRotation> resultMap =
                puzzleStateDto.getCoverage().stream().map(this::puzzleStateEntryDtoToEntry)
                        .collect(
                                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                        );
        puzzleState.setPositionedDetails(resultMap);
        return puzzleState;
    }

    ///// Puzzle Map
    PuzzleMap puzzleMapDtoToPuzzleMap(PuzzleMapDto puzzleMapDto);

    PuzzleMapDto puzzleMapToPuzzleMapDto(PuzzleMap puzzleMap);

    //// Puzzle Config
    PuzzleConfig puzzleConfigDtoToPuzzleConfig(PuzzleConfigDto puzzleConfigDto);

    PuzzleConfigDto puzzleConfigToPuzzleConfigDto(PuzzleConfig puzzleConfig);
}
