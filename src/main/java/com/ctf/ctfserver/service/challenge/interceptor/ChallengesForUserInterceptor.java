package com.ctf.ctfserver.service.challenge.interceptor;

import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import java.util.function.BiPredicate;

public class ChallengesForUserInterceptor implements BaseChallengeInterceptor {

    private final BiPredicate<ChallengeServiceModel, String> hasUser = (challengeServiceModel, userId) ->
            challengeServiceModel.getParticipants().stream()
                    .anyMatch(participant -> participant.getId().equals(userId));

    public static ChallengesForUserInterceptor getInstance() {
        return new ChallengesForUserInterceptor();
    }
    @Override
    public ChallengeServiceModel process(ChallengeServiceModel challengeServiceModel, String... args) {
        String userId = args[0];
        return this.hasUser.test(challengeServiceModel, userId) ? challengeServiceModel : null;
    }
}
