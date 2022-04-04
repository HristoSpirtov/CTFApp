package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.models.binding.ChallengeCreateBindingModel;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChallengeMapper {

    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);

    ChallengeServiceModel challengeCreateBindingModelToChallengeServiceModel(ChallengeCreateBindingModel challengeCreateBindingModel);
    Challenge challengeServiceModelToChallenge(ChallengeServiceModel challengeServiceModel);
    ChallengeServiceModel ChallengeToChallengeServiceModel(Challenge challenge);
}
