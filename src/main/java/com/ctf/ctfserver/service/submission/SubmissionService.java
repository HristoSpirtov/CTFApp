package com.ctf.ctfserver.service.submission;

import com.ctf.ctfserver.domain.models.service.ChartServiceModel;
import com.ctf.ctfserver.domain.models.service.SolvedSubmissionServiceModel;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;

import java.util.Arrays;
import java.util.List;

public interface SubmissionService {
    SubmissionServiceModel createSubmission(SubmissionServiceModel submissionServiceModel);

    List<SubmissionServiceModel> getSubmissions(String type, String id);

    void deleteSubmissions(List<String> submissionServiceModels);

    List<SolvedSubmissionServiceModel> getSolvesForUser(String userId);

    List<SolvedSubmissionServiceModel> getFailsForUser(String userId);

    void solveSubmissions(List<String> submissionServiceModels, String userId);

    List<ChartServiceModel> getUserScoreOverTime(String userId);
}
