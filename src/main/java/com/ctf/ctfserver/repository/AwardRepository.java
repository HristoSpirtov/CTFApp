package com.ctf.ctfserver.repository;

import com.ctf.ctfserver.domain.entities.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award, String> {

    List<Award> findAllByUserId(String id);

    @Modifying
    @Query("delete from Award a where a.id = :id")
    void deleteAwards(@Param("id") String id);
}
