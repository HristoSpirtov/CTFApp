package com.ctf.ctfserver.domain.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity  {

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "school",
            nullable = false)
    private String school;

    @Column(name = "username",
            nullable = false,
            unique = true,
            updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email",
            nullable = false,
            unique = true)
    private String email;


    @Column(name = "verified", nullable = false)
    private boolean isVerified;
    @Column(name = "hidden", nullable = false)
    private boolean isHidden;
    @Column(name = "banned", nullable = false)
    private boolean isBanned;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<Role> roles;

    @OneToMany(targetEntity = Award.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Collection<Award> awards;

}
