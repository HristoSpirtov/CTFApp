package com.ctf.ctfserver.repository;

import com.ctf.ctfserver.domain.entities.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository  extends JpaRepository<Challenge, String> {
    Challenge findByName(String name);
}
