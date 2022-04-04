package com.ctf.ctfserver.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "challenges")
public class Challenge extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "value", nullable = false)
    private Integer value;

    @OneToMany(targetEntity = Flag.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "challenge_id")
    private Collection<Flag> flags = new java.util.ArrayList<>();


}
