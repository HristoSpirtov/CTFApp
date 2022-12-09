package com.ctf.ctfserver.domain.entities;

import com.ctf.ctfserver.domain.enums.IconType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "awards")
public class Award extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "value")
    private Integer value;

    @Column(name = "category")
    private String category;

    @Column(name = "icon")
    @Enumerated(EnumType.STRING)
    private IconType icon;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
