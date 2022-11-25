package com.ctf.ctfserver.domain.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class SolvedSubmissionServiceModel {
    private String id;
    private String user;
    private String school;
    private String challenge;
    private String challengeId;
    private Integer value;
    private String type;
    private String provided;
    private Timestamp date;
}
