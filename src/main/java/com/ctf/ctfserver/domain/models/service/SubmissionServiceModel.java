package com.ctf.ctfserver.domain.models.service;

import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.enums.SubmissionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class SubmissionServiceModel {

    private String user;
    private String school;
    private String challenge;
    private String type;
    private String provided;
    private Timestamp date;

}
