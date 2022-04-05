package com.ctf.ctfserver.domain.models.binding;


import com.ctf.ctfserver.domain.entities.Submission;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UsersEditOrDeleteBindingModel {
    private String id;
    private String name;
    private String school;
    private String username;
    private String email;
    private List<SubmissionServiceModel> submissions;
    private boolean isVerified;
    private boolean isHidden;
    private boolean isBanned;
}
