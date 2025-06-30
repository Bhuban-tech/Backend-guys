package com.example.back_end.Controller;


import com.example.back_end.Entity.Application;
import com.example.back_end.Entity.Job;
import com.example.back_end.Entity.User;
import com.example.back_end.Repository.ApplicationRepository;
import com.example.back_end.Repository.JobRepository;
import com.example.back_end.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("/api")
public class JobController {


    @Autowired
    private JobRepository jobRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ApplicationRepository appRepo;


    @PostMapping("/post/{userId}")
    public String postJob(@PathVariable Long userId,
                          @RequestBody Job job) {
        // 1.1 check admin
        User user = userRepo.findById(userId).orElse(null);
        if (user == null || !user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            return "Only admins can post jobs";
        }
        if (job == null) {
            return "Please provide job data";
        }
        if (job.getJobName() == null || job.getJobName().trim().isEmpty()) {
            return "Job name is required";
        }
        if (job.getJobDescription() == null || job.getJobDescription().trim().isEmpty()) {
            return "Job description is required";
        }
        if (jobRepo.findByJobName(job.getJobName()).isPresent()) {
            return "This job is already posted";
        }
        if (jobRepo.findByJobDescription(job.getJobDescription()).isPresent()) {
            return "A job with that description already exists";
        }
        job.setUser(user);
        job.setJobCreationDate(LocalDateTime.now());
        jobRepo.save(job);

        return "Job posted successfully";
    }


    @GetMapping("/joblist")
    public List<Job> getAllJobs() {
        return jobRepo.findAll();
    }


    @PostMapping("/apply/{jobId}")
    public String applyToJob(@PathVariable Long jobId,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email,
                             @RequestParam("file") MultipartFile file) {
        try {
            if (name == null || name.isEmpty()) return "Name is required";
            if (email == null || email.isEmpty()) return "Email is required";
            if (file == null || file.isEmpty()) return "CV file is required";
            Job job = jobRepo.findById(jobId).orElse(null);
            if (job == null) {
                return "Job not found";
            }
            Application application = new Application();
            application.setCandidateName(name);
            application.setCandidateEmail(email);
            application.setApplicationDate(LocalDateTime.now());
            application.setStatus("Pending");
            application.setJob(job);
            application.setCvFile(file.getBytes());
            application.setCvFileName(file.getOriginalFilename());
            application.setCvFileType(file.getContentType());
            appRepo.save(application);
            return "Application submitted";
        } catch (Exception e) {
            return "Check your detailss: " + e.getMessage();
        }
    }

    @GetMapping("/admin/{adminId}/job/{jobId}")
    public Object getApplicationsForJobByAdmin(
            @PathVariable Long adminId,
            @PathVariable Long jobId) {

        Job job = jobRepo.findById(jobId).orElse(null);
        if (job == null) {
            return "Job not found";
        }
        if (!job.getUser().getUserId().equals(adminId)) {
            return "youre not eligible to view";
        }
        List<Application> applications = appRepo.findByJob(job);
        return applications;
    }


    @GetMapping("/userlist")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
