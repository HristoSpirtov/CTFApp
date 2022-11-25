package com.ctf.ctfserver.api;

import com.ctf.ctfserver.domain.models.service.ChartServiceModel;
import com.ctf.ctfserver.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChartController {

    private final SubmissionService submissionService;


    @GetMapping("/chart/user/score/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChartServiceModel>> getUserScoreOverTime(@PathVariable String userId) {
        return ResponseEntity.ok().body(this.submissionService.getUserScoreOverTime(userId));
    }
}
