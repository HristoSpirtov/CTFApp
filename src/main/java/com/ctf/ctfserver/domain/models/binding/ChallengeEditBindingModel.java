package com.ctf.ctfserver.domain.models.binding;

import com.ctf.ctfserver.domain.models.response.UserResponseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ChallengeEditBindingModel {

    private String name;
    private String description;
    private Integer value;
    private List<UserResponseModel> participants;
    private String state;
}
