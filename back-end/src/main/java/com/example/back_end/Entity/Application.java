package com.example.back_end.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    private String candidateName;
    private String candidateEmail;
    private LocalDateTime applicationDate;
    private String status;

@Lob
private byte[] cvFile;
private String cvFileName;
private String cvFileType;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
}
