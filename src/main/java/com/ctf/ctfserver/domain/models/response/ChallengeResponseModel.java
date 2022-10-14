package com.ctf.ctfserver.domain.models.response;

import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class ChallengeResponseModel {

    private String id;
    private String name;
    private String description;
    private Integer value;
    private String flag;
    private Collection<SubmissionResponseModel> submissions;
    private Collection<UserResponseModel> participants;
    private String type;
    private String state;
}
