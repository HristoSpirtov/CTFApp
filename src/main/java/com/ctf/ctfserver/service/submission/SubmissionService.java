package com.ctf.ctfserver.service.submission;

import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;

public interface SubmissionService {
    SubmissionServiceModel createSubmission(SubmissionServiceModel submissionServiceModel);
}
