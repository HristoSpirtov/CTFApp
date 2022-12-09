package com.ctf.ctfserver.domain.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@NoArgsConstructor
@Getter
@Setter
public class AwardResponseModel {
    private String id;
    private String name;
    private String description;
    private String date;
    private Integer value;
    private String category;
    private String icon;
    private String userId;
}
