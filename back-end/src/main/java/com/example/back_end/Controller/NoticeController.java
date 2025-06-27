package com.example.back_end.Controller;

import com.example.back_end.Dto.NoticeDtos;
import com.example.back_end.Service.NoticeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final ObjectMapper objectMapper;

    public NoticeController(NoticeService noticeService, ObjectMapper objectMapper) {
        this.noticeService = noticeService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNoticeHandler(
            @RequestPart("file") MultipartFile file,
            @RequestPart("noticeDto") String noticeDto) {
        try {
            NoticeDtos dto = objectMapper.readValue(noticeDto, NoticeDtos.class);
            NoticeDtos savedDto = noticeService.addNotice(dto, file);
            return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<NoticeDtos> getNoticeHandler(@PathVariable Integer id) {
        NoticeDtos dto = noticeService.getNoticeById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoticeDtos>> getAllNoticeHandler() {
        List<NoticeDtos> all = noticeService.getAll();
        return ResponseEntity.ok(all);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<NoticeDtos> updateNoticeHandler(
            @PathVariable Integer id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("noticeDto") String noticeDto) throws IOException {

        NoticeDtos dto = objectMapper.readValue(noticeDto, NoticeDtos.class);
        dto.setId(id);
        NoticeDtos updatedDto = noticeService.updateNotice(dto, file);

        return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNoticeHandler(@PathVariable Integer id) {
        boolean deleted = noticeService.deleteNotice(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
