package com.ctf.ctfserver.domain.entities;

import com.ctf.ctfserver.domain.enums.SubmissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "submissions")
public class Submission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="school", nullable = false)
    private String school;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable = false)
    private SubmissionType type;

    @Column(name = "provided", nullable = false)
    private String provided;

    @Column(name = "date")
    private Timestamp date;

}
