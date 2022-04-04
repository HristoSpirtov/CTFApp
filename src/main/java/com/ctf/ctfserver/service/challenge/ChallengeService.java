package com.ctf.ctfserver.service.challenge;

import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;

import java.util.List;

public interface ChallengeService {
    void createChallenge(ChallengeServiceModel challengeServiceModel);

    List<ChallengeServiceModel> getAllChallenges();
}
