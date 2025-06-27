package com.example.back_end.Repository;

import com.example.back_end.Entity.Application;
import com.example.back_end.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJob (Job job);
}
