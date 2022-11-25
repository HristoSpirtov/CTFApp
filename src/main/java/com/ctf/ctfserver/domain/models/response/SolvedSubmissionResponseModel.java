package com.ctf.ctfserver.domain.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class SolvedSubmissionResponseModel {
    private String id;
    private String user;
    private String school;
    private String challenge;
    private String challengeId;
    private Integer value;
    private String type;
    private String provided;
    private String date;
}
