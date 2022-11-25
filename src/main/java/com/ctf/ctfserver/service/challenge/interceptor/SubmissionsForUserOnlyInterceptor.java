package com.ctf.ctfserver.service.challenge.interceptor;


import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;

import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class SubmissionsForUserOnlyInterceptor implements BaseChallengeInterceptor {

    private final BiPredicate<SubmissionServiceModel, String> hasUser = (submissionServiceModel, userId) ->
            submissionServiceModel.getUser().equals(userId);

    public static SubmissionsForUserOnlyInterceptor getInstance() {
        return new SubmissionsForUserOnlyInterceptor();
    }

    @Override
    public ChallengeServiceModel process(ChallengeServiceModel challenge, String... args) {
        Collection<SubmissionServiceModel> submissionServiceModels = challenge.getSubmissions();
        String id = args[0];
        List<SubmissionServiceModel> filtered = submissionServiceModels.stream()
                .filter(submission -> this.hasUser.test(submission, id))
                .collect(Collectors.toList());

        challenge.setSubmissions(filtered);
        return challenge;
    }
}
