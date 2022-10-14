package com.ctf.ctfserver.domain.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SubmissionResponseModel {
    private String id;
    private String user;
    private String school;
    private String challenge;
    private String type;
    private String provided;
    private String date;
}
