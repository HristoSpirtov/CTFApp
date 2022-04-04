package com.ctf.ctfserver.domain.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlagMapper {

    FlagMapper INSTANCE = Mappers.getMapper(FlagMapper.class);


}
