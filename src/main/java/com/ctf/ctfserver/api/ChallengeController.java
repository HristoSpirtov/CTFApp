package com.ctf.ctfserver.api;

import com.ctf.ctfserver.domain.HttpResponse;
import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.models.binding.ChallengeCreateBindingModel;
import com.ctf.ctfserver.domain.models.binding.UserRegisterBindingModel;
import com.ctf.ctfserver.domain.models.mapper.ChallengeMapper;
import com.ctf.ctfserver.domain.models.mapper.UserMapper;
import com.ctf.ctfserver.domain.models.response.UserResponseModel;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;
import com.ctf.ctfserver.service.challenge.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.ctf.ctfserver.exception.ExceptionHandling.CHALLENGE_CREATED_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChallengeController {

    private final ChallengeService challengeService;


    @PostMapping("/challenge/new")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> createChallenge(@RequestBody ChallengeCreateBindingModel challengeCreateBindingModel) {

        this.challengeService.createChallenge(ChallengeMapper
                .INSTANCE.challengeCreateBindingModelToChallengeServiceModel(challengeCreateBindingModel));

        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                HttpStatus.CREATED.getReasonPhrase().toUpperCase(),
                CHALLENGE_CREATED_SUCCESSFULLY), HttpStatus.CREATED);
    }

    @GetMapping("/challenge/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChallengeServiceModel>> getAllChallenges() {

        return ResponseEntity.ok().body(this.challengeService.getAllChallenges());
    }
}
