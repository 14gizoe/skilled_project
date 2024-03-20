package com.project.skilled_project.domain.file.service;

import com.project.skilled_project.domain.file.dto.FileResponseDto;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  List<FileResponseDto> upload(Long sourceId, String category, MultipartFile[] files) throws IOException;

  byte[] download(String fileKey) throws IOException;

  String getFilePath(Long sourceId, String category);
}
