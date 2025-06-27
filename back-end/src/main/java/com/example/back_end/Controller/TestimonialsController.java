package com.example.back_end.Controller;


import com.example.back_end.Dto.TestimonialsDtos;
import com.example.back_end.Service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
public class TestimonialsController {

    private final CompanyService companyService;
    private final ObjectMapper objectMapper;

    public TestimonialsController(CompanyService companyService, ObjectMapper objectMapper) {
        this.companyService = companyService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<TestimonialsDtos> addTestimonialHandler(
            @RequestPart("file") MultipartFile file,
            @RequestPart("testimonialDtoStr") String testimonialDtoStr) throws IOException {

        TestimonialsDtos dto = objectMapper.readValue(testimonialDtoStr, TestimonialsDtos.class);
        TestimonialsDtos savedDto = companyService.addTestimonial(dto, file);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestimonialsDtos> getTestimonialHandler(@PathVariable Integer id) {
        TestimonialsDtos dto = companyService.getTestimonialById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<TestimonialsDtos>> getAllCompaniesHandler() {
        List<TestimonialsDtos> all = companyService.getAll();
        return ResponseEntity.ok(all);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<TestimonialsDtos> updateTestimonialHandler(
            @PathVariable Integer id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("testimonialDtoStr") String testimonialDtoStr) throws IOException {

        TestimonialsDtos dto = objectMapper.readValue(testimonialDtoStr, TestimonialsDtos.class);
        dto.setId(id);
        TestimonialsDtos updatedDto = companyService.updateTestimonial(dto, file);

        return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTestimonialHandler(@PathVariable Integer id) {
        boolean deleted = companyService.deleteTestimonial(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
