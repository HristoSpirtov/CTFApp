package com.ctf.ctfserver.service.challenge;

import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.domain.models.service.FlagServiceModel;

import java.util.List;

public interface ChallengeService {
    void createChallenge(ChallengeServiceModel challengeServiceModel);

    List<ChallengeServiceModel> getAllChallengesForUser(String id);

    List<ChallengeServiceModel> getAllChallenges();

    void deleteChallenges(List<ChallengeServiceModel> challengeServiceModels);

    void editChallenges(List<ChallengeServiceModel> challengeServiceModels);

    ChallengeServiceModel getChallengeById(String id);

    List<FlagServiceModel> getAllFlagsForChallenge(String challengeId);

    ChallengeServiceModel editChallenge(ChallengeServiceModel challengeServiceModel, String challengeId);

    void deleteFlag(String flagId);

    void createFlag(FlagServiceModel flagServiceModel, String ChallengeId);

    void editFlag(FlagServiceModel flagCreateBindingModelToFlagServiceModel);
}
