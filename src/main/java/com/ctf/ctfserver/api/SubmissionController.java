package com.ctf.ctfserver.api;
import com.ctf.ctfserver.domain.HttpResponse;
import com.ctf.ctfserver.domain.models.mapper.FlagMapper;
import com.ctf.ctfserver.domain.models.mapper.SubmissionMapper;
import com.ctf.ctfserver.domain.models.response.SolvedSubmissionResponseModel;
import com.ctf.ctfserver.domain.models.response.SubmissionResponseModel;
import com.ctf.ctfserver.domain.models.service.SolvedSubmissionServiceModel;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import com.ctf.ctfserver.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ctf.ctfserver.exception.ExceptionHandling.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/submission/new")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SubmissionResponseModel> createSubmission(@RequestBody SubmissionServiceModel submissionServiceModel) {

        SubmissionResponseModel submissionResponseModel = SubmissionMapper.INSTANCE
                .submissionServiceModelToSubmissionResponseModel(this.submissionService
                        .createSubmission(submissionServiceModel));
        return new ResponseEntity<>(submissionResponseModel,
                HttpStatus.CREATED);
    }

    @GetMapping("/submissions/all/{type}/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<List<SubmissionResponseModel>> getAllSubmissions(@PathVariable String type, @PathVariable String id) {

        List<SubmissionResponseModel> submissions = this.submissionService.getSubmissions(type.toUpperCase(), id)
                .stream()
                .map(SubmissionMapper.INSTANCE::submissionServiceModelToSubmissionResponseModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(submissions);
    }

    @GetMapping("/submissions/solves/{userId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<List<SolvedSubmissionResponseModel>> getAllSubmissions(@PathVariable String userId) {

        List<SolvedSubmissionResponseModel> submissions = this.submissionService.getSolvesForUser(userId).stream()
                .map(SubmissionMapper.INSTANCE::solvedSubmissionServiceModelToSolvedSubmissionResponseModel).collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(submissions);
    }

    @GetMapping("/submissions/fails/{userId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<List<SolvedSubmissionResponseModel>> getAllSubmissionsFails(@PathVariable String userId) {

        List<SolvedSubmissionResponseModel> submissions = this.submissionService.getFailsForUser(userId).stream()
                .map(SubmissionMapper.INSTANCE::solvedSubmissionServiceModelToSolvedSubmissionResponseModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(submissions);
    }

    @PostMapping("/submissions/delete")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> deleteSubmissions(@RequestBody List<String> submissionServiceModels) {

        this.submissionService.deleteSubmissions(submissionServiceModels);
        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                SUBMISSIONS_DELETED_SUCCESSFULLY.toUpperCase()),
                HttpStatus.OK);
    }

    @PostMapping("/submissions/solve/{userId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> solveSubmissions(@RequestBody List<String> submissionServiceModels, @PathVariable String userId) {

        this.submissionService.solveSubmissions(submissionServiceModels, userId);
        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                SUBMISSIONS_SOLVED_BY_ADMIN_SUCCESSFULLY.toUpperCase()),
                HttpStatus.OK);
    }
}
