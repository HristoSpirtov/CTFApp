package com.ctf.ctfserver.domain.models.service;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FlagServiceModel {

    private String id;
    private String flag;
    private ChallengeServiceModel challengeServiceModel;
}
