package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Challenge;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

@Mapper(mappingControl = DeepClone.class)
public interface CloningMapper {
    CloningMapper INSTANCE = Mappers.getMapper( CloningMapper.class );

    Challenge clone(Challenge challenge);
}
