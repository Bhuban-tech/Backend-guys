package com.example.back_end.Service;


import com.example.back_end.Dto.TestimonialsDtos;
import com.example.back_end.Entity.TestimonialsEntity;
import com.example.back_end.Repository.TestimonialsRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TestimonialsServiceImpl implements CompanyService {

    private final TestimonialsRepo testimonialRepository;

    public TestimonialsServiceImpl(TestimonialsRepo testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @Override
    public TestimonialsDtos addTestimonial(TestimonialsDtos dto, MultipartFile file) throws IOException {
        dto.setPosterData(file.getBytes());
        TestimonialsEntity entity = convertDtoToEntity(dto);
        TestimonialsEntity savedEntity = testimonialRepository.save(entity);
        return convertEntityToDto(savedEntity);
    }

    @Override
    public TestimonialsDtos getTestimonialById(Integer id) {
        return testimonialRepository.findById(id)
                .map(this::convertEntityToDto)
                .orElse(null);
    }

    @Override
    public List<TestimonialsDtos> getAll() {
        return testimonialRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }


    @Override
    public TestimonialsDtos updateTestimonial(TestimonialsDtos dto, MultipartFile file) throws IOException {
        Optional<TestimonialsEntity> optional = testimonialRepository.findById(dto.getId());
        if (optional.isEmpty()) return null;

        TestimonialsEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        if (file != null && !file.isEmpty()) {
            entity.setPosterData(file.getBytes());
        }

        TestimonialsEntity updated = testimonialRepository.save(entity);
        return convertEntityToDto(updated);
    }

    @Override
    public boolean deleteTestimonial(Integer id) {
        if (testimonialRepository.existsById(id)) {
            testimonialRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TestimonialsEntity convertDtoToEntity(TestimonialsDtos dto) {
        TestimonialsEntity entity = new TestimonialsEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPosterData(dto.getPosterData());
        return entity;
    }

    private TestimonialsDtos convertEntityToDto(TestimonialsEntity entity) {
        TestimonialsDtos dto = new TestimonialsDtos();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPosterData(entity.getPosterData());
        return dto;
    }
}