package com.example.back_end.Service;

import com.example.back_end.Dto.NoticeDtos;
import com.example.back_end.Entity.NoticeE;
import com.example.back_end.Repository.NoticeRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepo noticeRepo;

    public NoticeServiceImpl(NoticeRepo noticeRepo) {
        this.noticeRepo = noticeRepo;
    }

    @Override
    public NoticeDtos addNotice(NoticeDtos dto, MultipartFile file) throws IOException {
        dto.setNoticeImage(file.getBytes());
        NoticeE entity = convertDtoToEntity(dto);
        NoticeE savedEntity = noticeRepo.save(entity);
        return convertEntityToDto(savedEntity);
    }

    @Override
    public NoticeDtos getNoticeById(Integer id) {
        return noticeRepo.findById(id)
                .map(this::convertEntityToDto)
                .orElse(null);
    }

    @Override
    public List<NoticeDtos> getAll() {
        return noticeRepo.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }


    @Override
    public NoticeDtos updateNotice(NoticeDtos dto, MultipartFile file) throws IOException {
        Optional<NoticeE> optional = noticeRepo.findById(dto.getId());
        if (optional.isEmpty()) return null;

        NoticeE entity = optional.get();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        if (file != null && !file.isEmpty()) {
            entity.setNoticeImage(file.getBytes());
        }

        NoticeE updated = noticeRepo.save(entity);
        return convertEntityToDto(updated);
    }

    @Override
    public boolean deleteNotice(Integer id) {
        if (noticeRepo.existsById(id)) {
            noticeRepo.deleteById(id);
            return true;
        }
        return false;
    }

    private NoticeE convertDtoToEntity(NoticeDtos dto) {
        NoticeE entity = new NoticeE();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setNoticeImage(dto.getNoticeImage());
        return entity;
    }

    private NoticeDtos convertEntityToDto(NoticeE entity) {
        NoticeDtos dto = new NoticeDtos();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setNoticeImage(entity.getNoticeImage());
        return dto;
    }
}
