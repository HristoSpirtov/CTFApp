package com.ctf.ctfserver.api;

import com.ctf.ctfserver.domain.HttpResponse;
import com.ctf.ctfserver.domain.models.binding.*;
import com.ctf.ctfserver.domain.models.mapper.ChallengeMapper;
import com.ctf.ctfserver.domain.models.mapper.FlagMapper;
import com.ctf.ctfserver.domain.models.mapper.UserMapper;
import com.ctf.ctfserver.domain.models.response.ChallengeResponseModel;
import com.ctf.ctfserver.domain.models.response.FlagResponseModel;
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
import java.util.stream.Collectors;

import static com.ctf.ctfserver.exception.ExceptionHandling.*;

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

    @GetMapping("/challenge/all/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChallengeResponseModel>> getAllChallengesForUser(@PathVariable String id) {

        List<ChallengeResponseModel> allChallengesForUser =
                this.challengeService.getAllChallengesForUser(id).stream()
                        .map(ChallengeMapper.INSTANCE::ChallengeServiceModelToChallengeResponseModel)
                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(allChallengesForUser);
    }

    @GetMapping("/challenge/missing/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChallengeResponseModel>> getAllMissingChallengesForUser(@PathVariable String id) {

        List<ChallengeResponseModel> allChallengesForUser =
                this.challengeService.getAllMissingChallengesForUser(id).stream()
                        .map(ChallengeMapper.INSTANCE::ChallengeServiceModelToChallengeResponseModel)
                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(allChallengesForUser);
    }

    @GetMapping("/challenge/all")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<List<ChallengeServiceModel>> getAllChallenges() {

        return ResponseEntity.ok().body(this.challengeService.getAllChallenges());
    }

    @PostMapping("/challenges/delete")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> deleteChallenges(@RequestBody final List<ChallengesEditOrDeleteBindingModel> challenges) {
        List<ChallengeServiceModel> challengeServiceModels = ChallengeMapper.INSTANCE
                .listChallengeDeleteBindingToListChallengeServiceModel(challenges);
        this.challengeService.deleteChallenges(challengeServiceModels);

        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                CHALLENGES_DELETED_SUCCESSFULLY.toUpperCase()), HttpStatus.OK);
    }

    @PatchMapping("/challenges/edit")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> editChallenges(@RequestBody final List<ChallengesEditOrDeleteBindingModel> challenges) throws UserNotFoundException, UsernameExistsException {
        List<ChallengeServiceModel> challengeServiceModels = ChallengeMapper.INSTANCE
                .listChallengeDeleteBindingToListChallengeServiceModel(challenges);
        this.challengeService.editChallenges(challengeServiceModels);

        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                CHALLENGES_EDITED_SUCCESSFULLY.toUpperCase()),
                HttpStatus.OK);
    }

    @PatchMapping("/challenge/edit/{challengeId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<ChallengeResponseModel> editChallenge(@RequestBody ChallengeEditBindingModel challenge, @PathVariable String challengeId) {

        return ResponseEntity.ok()
                .body(ChallengeMapper.INSTANCE
                        .ChallengeServiceModelToChallengeResponseModel(this.challengeService
                                .editChallenge(ChallengeMapper.INSTANCE
                                        .challengeEditBindingModelToChallengeServiceModel(challenge), challengeId)));
    }

    @GetMapping("/challenge/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<ChallengeResponseModel> getChallengeById(@PathVariable String id) {

        ChallengeResponseModel challengeById = ChallengeMapper.INSTANCE.ChallengeServiceModelToChallengeResponseModel(this.challengeService.getChallengeById(id));
        return ResponseEntity.ok()
                .body(challengeById);
    }

    @GetMapping("/flag/all/{challengeId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<List<FlagResponseModel>> getAllFlagsForChallenge(@PathVariable String challengeId) {

        return ResponseEntity.ok()
                .body(FlagMapper.INSTANCE
                        .listFlagServiceModelsToListFlagResponseModels(challengeService
                                .getAllFlagsForChallenge(challengeId)));

    }

    @DeleteMapping("/flag/delete/{flagId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> deleteFlag(@PathVariable String flagId) {
        this.challengeService.deleteFlag(flagId);
        return new ResponseEntity<>(new HttpResponse(new Date(),
            HttpStatus.OK.value(),
            HttpStatus.OK,
            HttpStatus.OK.getReasonPhrase().toUpperCase(),
            FLAG_DELETED_SUCCESSFULLY.toUpperCase()),
            HttpStatus.OK);
    }

    @PostMapping("/flag/create/{challengeId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> createFlag(@RequestBody FlagCreateBindingModel flagCreateBindingModel, @PathVariable String challengeId) {
        this.challengeService.createFlag(FlagMapper.INSTANCE.flagCreateBindingModelToFlagServiceModel(flagCreateBindingModel), challengeId);
        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                FLAG_CREATED_SUCCESSFULLY.toUpperCase()),
                HttpStatus.OK);
    }

    @PatchMapping("/flag/edit")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> editFlag(@RequestBody FlagCreateBindingModel flagCreateBindingModel) {
        this.challengeService.editFlag(FlagMapper.INSTANCE.flagCreateBindingModelToFlagServiceModel(flagCreateBindingModel));
        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                FLAG_EDITED_SUCCESSFULLY.toUpperCase()),
                HttpStatus.OK);
    }
}
