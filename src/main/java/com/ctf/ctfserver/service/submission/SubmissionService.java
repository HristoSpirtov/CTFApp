package com.ctf.ctfserver.service.submission;

import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;

import java.util.List;

public interface SubmissionService {
    SubmissionServiceModel createSubmission(SubmissionServiceModel submissionServiceModel);

    List<SubmissionServiceModel> getSubmissions(String type, String id);

    void deleteSubmissions(List<String> submissionServiceModels);
}
