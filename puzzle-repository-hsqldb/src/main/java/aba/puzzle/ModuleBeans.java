package aba.puzzle;

import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper;
import aba.puzzle.mapper.PersistenceMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleBeans {

    @Bean
    public MapStructMapper getMapStructMapper() {
        return MapStructMapper.INSTANCE;
    }

    @Bean
    public PersistenceMapper getPersistenceMapper() {
        return Mappers.getMapper(PersistenceMapper.class);
    }
}
