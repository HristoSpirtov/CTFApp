package com.ctf.ctfserver.domain.models.binding;

import com.ctf.ctfserver.domain.models.response.UserResponseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
public class ChallengesEditOrDeleteBindingModel {
    private String id;
    private String name;
    private String description;
    private Integer value;
    private String flag;
    private List<UserResponseModel> participants;
    private String type;
    private String state;
}
