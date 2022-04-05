package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.entities.Submission;
import com.ctf.ctfserver.domain.models.binding.ChallengeCreateBindingModel;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper
public interface ChallengeMapper {

    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);

    ChallengeServiceModel challengeCreateBindingModelToChallengeServiceModel(ChallengeCreateBindingModel challengeCreateBindingModel);

    @Mapping(target = "submissions", ignore = true)
    Challenge challengeServiceModelToChallenge(ChallengeServiceModel challengeServiceModel);


    @Mapping(source = "submissions", target = "submissions", qualifiedByName = "submissionMapping")
    ChallengeServiceModel ChallengeToChallengeServiceModel(Challenge challenge);


    @Named("submissionMapping")
    default Collection<SubmissionServiceModel> convertSubmissionsToServiceSubmissions(Collection<Submission> submissions) {

        return submissions.stream()
                .map(SubmissionMapper.INSTANCE::submissionToSubmissionServiceModel)
                .collect(Collectors.toList());
    }
}
