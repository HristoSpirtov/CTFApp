package com.ctf.ctfserver.domain.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChallengeServiceModel {
    private String id;
    private String name;
    private String description;
    private Integer value;
    private String flag;
}
