package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Flag;
import com.ctf.ctfserver.domain.models.binding.FlagCreateBindingModel;
import com.ctf.ctfserver.domain.models.response.FlagResponseModel;
import com.ctf.ctfserver.domain.models.service.FlagServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FlagMapper {

    FlagMapper INSTANCE = Mappers.getMapper(FlagMapper.class);

    List<FlagServiceModel> listFlagsToListFlagServiceModels(List<Flag> flags);

    List<FlagResponseModel> listFlagServiceModelsToListFlagResponseModels(List<FlagServiceModel> flagServiceModels);

    FlagServiceModel flagCreateBindingModelToFlagServiceModel(FlagCreateBindingModel flagCreateBindingModel);


}
