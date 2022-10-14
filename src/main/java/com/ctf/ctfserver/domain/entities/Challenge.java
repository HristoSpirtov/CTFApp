package com.ctf.ctfserver.domain.entities;

import com.ctf.ctfserver.domain.enums.ChallengeState;
import com.ctf.ctfserver.domain.enums.SubmissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(targetEntity = Flag.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "challenge_id")
    private Collection<Flag> flags = new java.util.ArrayList<>();

    @OneToMany(targetEntity = Submission.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "challenge_id")
    private Collection<Submission> submissions;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_challenge",
            joinColumns = @JoinColumn(
                    name = "challenge_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            )
    )
    private Set<User> participants = new HashSet<>();

    @Column(name = "type", nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name="state", nullable = false)
    private ChallengeState state;

}
