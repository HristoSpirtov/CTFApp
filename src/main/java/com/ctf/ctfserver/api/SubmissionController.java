package com.ctf.ctfserver.api;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import com.ctf.ctfserver.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/submission/new")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SubmissionServiceModel> createSubmission(@RequestBody SubmissionServiceModel submissionServiceModel) {


        return new ResponseEntity<>(this.submissionService
                .createSubmission(submissionServiceModel),
                HttpStatus.CREATED);
    }

}
