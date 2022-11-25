package com.ctf.ctfserver.service.challenge.interceptor;

import com.ctf.ctfserver.domain.enums.SubmissionType;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChallengeWithCorrectSubmissionInterceptor implements BaseChallengeInterceptor {

    private final Predicate<SubmissionServiceModel> isCorrect = (submissionServiceModel) ->
            submissionServiceModel.getType().equals(SubmissionType.CORRECT.name());

    public static ChallengeWithCorrectSubmissionInterceptor getInstance() {
        return new ChallengeWithCorrectSubmissionInterceptor();
    }

    @Override
    public ChallengeServiceModel process(ChallengeServiceModel challenge, String... args) {
        Collection<SubmissionServiceModel> submissionServiceModels = challenge.getSubmissions();
        List<SubmissionServiceModel> filtered = submissionServiceModels.stream()
                .filter(this.isCorrect)
                .collect(Collectors.toList());
        challenge.setSubmissions(filtered);
        return challenge;
    }
}
