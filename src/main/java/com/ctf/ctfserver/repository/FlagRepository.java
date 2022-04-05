package com.ctf.ctfserver.repository;

import com.ctf.ctfserver.domain.entities.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlagRepository extends JpaRepository<Flag, String> {
    List<Flag> findAllByChallengeId(String id);
}
