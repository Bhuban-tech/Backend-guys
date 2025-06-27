package com.example.back_end.Repository;


import com.example.back_end.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByJobId(Long jobId);
    Optional<Job> findByJobName(String jobTitle);
    Optional<Job> findByJobDescription(String jobDescription);
}