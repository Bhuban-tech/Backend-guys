package com.example.back_end.Repository;


import com.example.back_end.Entity.TestimonialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestimonialsRepo extends JpaRepository<TestimonialsEntity, Integer> {
}