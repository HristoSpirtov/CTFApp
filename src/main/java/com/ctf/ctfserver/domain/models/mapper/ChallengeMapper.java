package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.entities.Submission;
import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.enums.ChallengeState;
import com.ctf.ctfserver.domain.models.binding.ChallengeCreateBindingModel;
import com.ctf.ctfserver.domain.models.binding.ChallengeEditBindingModel;
import com.ctf.ctfserver.domain.models.binding.ChallengesEditOrDeleteBindingModel;
import com.ctf.ctfserver.domain.models.binding.UsersEditOrDeleteBindingModel;
import com.ctf.ctfserver.domain.models.response.ChallengeResponseModel;
import com.ctf.ctfserver.domain.models.response.SubmissionResponseModel;
import com.ctf.ctfserver.domain.models.response.UserResponseModel;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import com.ctf.ctfserver.service.challenge.ChallengeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper( imports = ChallengeState.class )
public interface ChallengeMapper {


    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);

    @Mapping(source = "participants", target = "participants", qualifiedByName = "participantsMapping")
    ChallengeServiceModel challengeCreateBindingModelToChallengeServiceModel(ChallengeCreateBindingModel challengeCreateBindingModel);

    @Mapping(target = "submissions", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(expression = "java( ChallengeState.valueOf(challengeServiceModel.getState().toUpperCase()) )", target = "state")
    Challenge challengeServiceModelToChallenge(ChallengeServiceModel challengeServiceModel);


    @Mapping(source = "submissions", target = "submissions", qualifiedByName = "submissionMapping")
    @Mapping(expression = "java( String.valueOf(challenge.getState().name()).toLowerCase() )", target = "state")
    ChallengeServiceModel ChallengeToChallengeServiceModel(Challenge challenge);

    @Mapping(source = "participants", target = "participants", qualifiedByName = "participantsRevMapping")
    @Mapping(source = "submissions", target = "submissions", qualifiedByName = "dateMapping")
    ChallengeResponseModel ChallengeServiceModelToChallengeResponseModel(ChallengeServiceModel challengeServiceModel);

    List<ChallengeServiceModel> listChallengeDeleteBindingToListChallengeServiceModel(List<ChallengesEditOrDeleteBindingModel> challengeDeleteBindingModel);

    @Mapping(target = "submissions", ignore = true)
    ChallengeServiceModel challengeEditBindingModelToChallengeServiceModel(ChallengeEditBindingModel challengeEditBindingModel);

    @Mapping(target = "submissions", ignore = true)
    ChallengeServiceModel challengeToChallengeServiceModel(Challenge challenge);

    @Named("submissionMapping")
    default Collection<SubmissionServiceModel> convertSubmissionsToServiceSubmissions(Collection<Submission> submissions) {

        return submissions.stream()
                .map(SubmissionMapper.INSTANCE::submissionToSubmissionServiceModel)
                .collect(Collectors.toList());
    }

    @Named("dateMapping")
    default Collection<SubmissionResponseModel> convertServiceSubmissionsToResponseSubmissions(Collection<SubmissionServiceModel> submissions) {

        return submissions.stream()
                .map(SubmissionMapper.INSTANCE::submissionServiceModelToSubmissionResponseModel)
                .collect(Collectors.toList());
    }

    @Named("participantsMapping")
    default Collection<UserServiceModel> convertParticipantsToServiceUsers(Collection<UserResponseModel> participants) {

        return participants.stream()
                .map(UserMapper.INSTANCE::userResponseModelToUserServiceModel)
                .collect(Collectors.toList());
    }

    @Named("participantsRevMapping")
    default Collection<UserResponseModel> convertParticipantsToResponseUsers(Collection<UserServiceModel> participants) {

        return participants.stream()
                .map(UserMapper.INSTANCE::userServiceModelToUserResponseModel)
                .collect(Collectors.toList());
    }

}
