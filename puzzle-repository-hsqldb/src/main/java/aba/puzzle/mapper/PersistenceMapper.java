package aba.puzzle.mapper;

import aba.puzzle.domain.*;
import aba.puzzle.persistence_dto.*;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface PersistenceMapper {

    //// Puzzle Config
    default PuzzleConfig puzzleConfigDtoToPuzzleConfig(PuzzleConfigDto puzzleConfigDto) {
        return new PuzzleConfig(
                puzzleConfigDto.getId(),
                puzzleConfigDto.getExtPuzzleConfigId(),
                new PuzzleMap(
                        puzzleConfigDto.getPuzzleFieldDtos().stream().map(puzzleFieldDto -> new PuzzleField(
                                puzzleFieldDto.getId(),
                                puzzleFieldDto.getShiftX(),
                                puzzleFieldDto.getShiftY()
                        )).collect(Collectors.toList())),
                puzzleConfigDto.getPuzzleDetailDtos().stream().map(puzzleDetailDto -> {
                    Detail detail = new Detail(
                            puzzleDetailDto.getId(), puzzleDetailDto.getExtId(),
                            new BallSide(Color.valueOf(puzzleDetailDto.getColorLeftSide()), BallPart.valueOf(puzzleDetailDto.getPartLeftSide())),
                            new BallSide(Color.valueOf(puzzleDetailDto.getColorUpperSide()), BallPart.valueOf(puzzleDetailDto.getPartUpperSide())),
                            new BallSide(Color.valueOf(puzzleDetailDto.getColorRightSide()), BallPart.valueOf(puzzleDetailDto.getPartRightSide())),
                            new BallSide(Color.valueOf(puzzleDetailDto.getColorLowerSide()), BallPart.valueOf(puzzleDetailDto.getPartLowerSide()))
                    );
                    detail.setAllowedRotations(puzzleDetailDto.getAllowedRotations().stream().map(PuzzleDetailRotationDto::getRotation).collect(Collectors.toList()));
                    return detail;
                }).collect(Collectors.toSet()));
    }

    default PuzzleConfigDto puzzleConfigToPuzzleConfigDto(PuzzleConfig puzzleConfig) {
        PuzzleConfigDto res = new PuzzleConfigDto();
        res.setExtPuzzleConfigId(puzzleConfig.getExtId());
        res.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        res.setPuzzleFieldDtos(puzzleConfig.getPuzzleMap().getPuzzleFields().stream().map(it -> {
            PuzzleFieldDto rs = new PuzzleFieldDto();
            rs.setShiftX(it.getShiftX());
            rs.setShiftY(it.getShiftY());
            rs.setPuzzleConfigDto(res);
            return rs;
        }).collect(Collectors.toSet()));
        res.setPuzzleDetailDtos(puzzleConfig.getPuzzleDetails().stream().map(it -> {
            PuzzleDetailDto rs = new PuzzleDetailDto();
            rs.setExtId(it.getExtId());
            rs.setColorLeftSide(it.getBallSide(DetailSide.left).getColor().name());
            rs.setColorUpperSide(it.getBallSide(DetailSide.up).getColor().name());
            rs.setColorRightSide(it.getBallSide(DetailSide.right).getColor().name());
            rs.setColorLowerSide(it.getBallSide(DetailSide.down).getColor().name());
            rs.setPartLeftSide(it.getBallSide(DetailSide.left).getBallPart().name());
            rs.setPartUpperSide(it.getBallSide(DetailSide.up).getBallPart().name());
            rs.setPartRightSide(it.getBallSide(DetailSide.right).getBallPart().name());
            rs.setPartLowerSide(it.getBallSide(DetailSide.down).getBallPart().name());
            rs.setPuzzleConfigDto(res);
            rs.setAllowedRotations(it.getAllowedRotations().stream().map(rotation -> {
                PuzzleDetailRotationDto rotationDto = new PuzzleDetailRotationDto();
                rotationDto.setPuzzleDetailDto(rs);
                rotationDto.setRotation(rotation);
                return rotationDto;
            }).collect(Collectors.toSet()));
            return rs;
        }).collect(Collectors.toSet()));
        return res;
    }

    //// Puzzle State

    default PuzzleState puzzleStateDtoToPuzzleState(PuzzleStateDto puzzleStateDto, PuzzleConfig puzzleConfig) {
        PuzzleState puzzleState = new PuzzleState(puzzleConfig);
        Map<Integer, Detail> details = puzzleConfig.getPuzzleDetails().stream().collect(Collectors.toMap(Detail::getId, it -> it));
        Map<Integer, PuzzleField> puzzleFieldMap = puzzleConfig.getPuzzleMap().getPuzzleFields().stream().collect(Collectors.toMap(PuzzleField::getId, it -> it));
        puzzleState.setPositionedDetails(puzzleStateDto.getPuzzleStateFieldDtos().stream().map(it ->
                        new AbstractMap.SimpleEntry<>(puzzleFieldMap.get(it.getPuzzleFieldId()),
                                new DetailWithRotation(details.get(it.getPuzzleDetailId()), it.getRotation())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
        return puzzleState;
    }

    default PuzzleStateDto puzzleStateToPuzzleStateDto(PuzzleState puzzleState) {
        PuzzleStateDto res = new PuzzleStateDto();
        Map<Integer, Detail> details = puzzleState.getPuzzleConfig().getPuzzleDetails().stream().collect(Collectors.toMap(Detail::getExtId, it -> it));
        Map<Map.Entry<Integer, Integer>, PuzzleField> puzzleFieldMap = puzzleState.getPuzzleConfig().getPuzzleMap().getPuzzleFields().stream()
                .collect(Collectors.toMap(it -> new AbstractMap.SimpleEntry<Integer, Integer>(it.getShiftX(), it.getShiftY()), it -> it));
        res.setPuzzleStateFieldDtos(puzzleState.getPositionedDetails().entrySet().stream().map(it -> {
            PuzzleStateFieldDto puzzleStateFieldDto = new PuzzleStateFieldDto();
            puzzleStateFieldDto.setPuzzleStateDto(res);
            puzzleStateFieldDto.setRotation(it.getValue().getRotation());
            Detail cd = details.get(it.getValue().getDetail().getExtId());
            puzzleStateFieldDto.setPuzzleDetailId(cd.getId());
            PuzzleField pf = puzzleFieldMap.get(new AbstractMap.SimpleEntry<>(it.getKey().getShiftX(), it.getKey().getShiftY()));
            puzzleStateFieldDto.setPuzzleFieldId(pf.getId());
            return puzzleStateFieldDto;
        }).collect(Collectors.toSet()));
        res.setPuzzleConfigId(puzzleState.getPuzzleConfig().getId());
        return res;
    }

}
