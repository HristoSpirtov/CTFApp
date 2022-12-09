package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Award;
import com.ctf.ctfserver.domain.enums.IconType;
import com.ctf.ctfserver.domain.models.response.AwardResponseModel;
import com.ctf.ctfserver.domain.models.service.AwardServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Mapper(imports = {Timestamp.class, SimpleDateFormat.class})
public interface AwardMapper {
    AwardMapper INSTANCE = Mappers.getMapper(AwardMapper.class);


    @Mapping(expression = "java( new Timestamp(System.currentTimeMillis()))", target = "date")
    @Mapping(source = "icon", target = "icon", qualifiedByName = "mapIconEnum")
    Award awardServiceModelToAward(AwardServiceModel awardServiceModel);

    @Mapping(source = "icon", target = "icon", qualifiedByName = "mapIconEnum")
    List<AwardServiceModel> awardToAwardServiceModels(List<Award> awards);

    @Mapping(expression = "java( new SimpleDateFormat(\"MMMM dd, hh:mm:ss aa\").format(awardServiceModel.getDate()))", target = "date")
    AwardResponseModel awardServiceModelToAwardResponseModel(AwardServiceModel awardServiceModel);

    @Named("mapIconEnum")
    default IconType mapIconEnum(String icon) {
        for (IconType test : IconType.values()) {
            if(test.getIconNAme().equals(icon)){
                return test;
            }
        }
        return null;
    }
}
