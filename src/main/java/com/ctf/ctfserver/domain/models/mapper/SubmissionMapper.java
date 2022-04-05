package com.ctf.ctfserver.domain.models.mapper;

import com.ctf.ctfserver.domain.entities.Submission;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubmissionMapper {

    SubmissionMapper INSTANCE = Mappers.getMapper(SubmissionMapper.class);

    @Mapping(expression = "java( String.valueOf(submission.getUser().getUsername()))", target = "user")
    @Mapping(expression = "java( String.valueOf(submission.getChallenge().getName()))", target = "challenge")
    @Mapping(expression = "java( String.valueOf(submission.getType().name()))", target = "type")
    SubmissionServiceModel submissionToSubmissionServiceModel(Submission submission);

}
