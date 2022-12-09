package com.ctf.ctfserver.api;

import com.ctf.ctfserver.domain.HttpResponse;
import com.ctf.ctfserver.domain.models.mapper.AwardMapper;
import com.ctf.ctfserver.domain.models.response.AwardResponseModel;
import com.ctf.ctfserver.domain.models.service.AwardServiceModel;
import com.ctf.ctfserver.service.award.AwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ctf.ctfserver.exception.ExceptionHandling.AWARD_CREATED_SUCCESSFULLY;
import static com.ctf.ctfserver.exception.ExceptionHandling.SUBMISSIONS_DELETED_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AwardController {
    private final AwardService awardService;

    @PostMapping("/award/new")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> awardUser(@RequestBody AwardServiceModel awardServiceModel) {

        this.awardService.createAward(awardServiceModel);

        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                HttpStatus.CREATED.getReasonPhrase().toUpperCase(),
                AWARD_CREATED_SUCCESSFULLY), HttpStatus.CREATED);
    }

    @GetMapping("/award/all/{userId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<List<AwardResponseModel>> getAllAwardsForUser(@PathVariable String userId) {

        List<AwardResponseModel> models = this.awardService.findAllAwardsForUser(userId).stream()
                .map(AwardMapper.INSTANCE::awardServiceModelToAwardResponseModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(models);
    }

    @PostMapping("/awards/delete")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> deleteAwards(@RequestBody List<String> awardServiceModels) {

        this.awardService.deleteSubmissions(awardServiceModels);
        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                SUBMISSIONS_DELETED_SUCCESSFULLY.toUpperCase()),
                HttpStatus.OK);
    }
}
