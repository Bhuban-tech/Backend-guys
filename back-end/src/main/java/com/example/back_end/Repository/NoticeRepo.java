package com.example.back_end.Repository;


import com.example.back_end.Entity.NoticeE;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepo extends JpaRepository<NoticeE, Integer> {}