package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Submission;
import com.ctf.ctfserver.domain.models.response.SolvedSubmissionResponseModel;
import com.ctf.ctfserver.domain.models.response.SubmissionResponseModel;
import com.ctf.ctfserver.domain.models.service.SolvedSubmissionServiceModel;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;

@Mapper(imports = SimpleDateFormat.class)
public interface SubmissionMapper {

    SubmissionMapper INSTANCE = Mappers.getMapper(SubmissionMapper.class);

    @Mapping(expression = "java( String.valueOf(submission.getUser().getUsername()))", target = "user")
    @Mapping(expression = "java( String.valueOf(submission.getChallenge().getName()))", target = "challenge")
    @Mapping(expression = "java( String.valueOf(submission.getType().name()))", target = "type")
    SubmissionServiceModel submissionToSubmissionServiceModel(Submission submission);

    @Mapping(expression = "java( submission.getChallenge().getId() )", target = "challengeId")
    @Mapping(expression = "java( String.valueOf(submission.getUser().getUsername()))", target = "user")
    @Mapping(expression = "java( String.valueOf(submission.getChallenge().getName()))", target = "challenge")
    @Mapping(expression = "java( String.valueOf(submission.getType().name()))", target = "type")
    @Mapping(expression = "java( submission.getChallenge().getValue() )", target = "value")
    SolvedSubmissionServiceModel submissionToSolvedSubmissionServiceModel(Submission submission);

    @Mapping(expression = "java( new SimpleDateFormat(\"MMMM dd, hh:mm:ss aa\").format(solvedSubmissionServiceModel.getDate()))", target = "date")
    SolvedSubmissionResponseModel solvedSubmissionServiceModelToSolvedSubmissionResponseModel(SolvedSubmissionServiceModel solvedSubmissionServiceModel);

    @Mapping(expression = "java( new SimpleDateFormat(\"MMMM dd, hh:mm:ss aa\").format(submissionServiceModel.getDate()))", target = "date")
    SubmissionResponseModel submissionServiceModelToSubmissionResponseModel(SubmissionServiceModel submissionServiceModel);

}
