package com.ctf.ctfserver.domain.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class AwardServiceModel {

    private String id;
    private String name;
    private String description;
    private Timestamp date;
    private Integer value;
    private String category;
    private String icon;
    private String userId;
}
