package com.ctf.ctfserver.repository;

import com.ctf.ctfserver.domain.entities.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlagRepository extends JpaRepository<Flag, String> {
}
