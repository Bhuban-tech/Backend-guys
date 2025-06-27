package com.example.back_end.Service;


import com.example.back_end.Dto.NoticeDtos;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoticeService {

    NoticeDtos addNotice(NoticeDtos dto, MultipartFile file) throws IOException;

    NoticeDtos getNoticeById(Integer id);

    List<NoticeDtos> getAll();

    NoticeDtos updateNotice(NoticeDtos dto, MultipartFile file) throws IOException;

    boolean deleteNotice(Integer id);
}
