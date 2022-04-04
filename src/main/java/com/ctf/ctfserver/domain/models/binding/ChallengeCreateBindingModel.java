package com.ctf.ctfserver.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ChallengeCreateBindingModel {

    private String name;
    private String description;
    private Integer value;
    private String flag;
}
