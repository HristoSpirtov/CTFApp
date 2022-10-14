package com.ctf.ctfserver.repository;

import com.ctf.ctfserver.domain.entities.Flag;
import com.ctf.ctfserver.domain.entities.Submission;
import com.ctf.ctfserver.domain.enums.SubmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {
    List<Submission> findAllByType(SubmissionType type);

    @Modifying
    @Query("delete from Submission s where s.id=:id")
    void deleteSubmissions(@Param("id") String id);
}
