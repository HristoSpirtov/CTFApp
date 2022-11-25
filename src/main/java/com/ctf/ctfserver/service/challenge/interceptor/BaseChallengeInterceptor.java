package com.ctf.ctfserver.service.challenge.interceptor;

import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;

public interface BaseChallengeInterceptor {
    ChallengeServiceModel process(ChallengeServiceModel challenge, String... args);
}
