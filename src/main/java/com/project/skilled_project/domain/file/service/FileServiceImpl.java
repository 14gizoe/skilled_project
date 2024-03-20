package com.project.skilled_project.domain.file.service;

import com.project.skilled_project.domain.file.dto.FileResponseDto;
import com.project.skilled_project.domain.file.entity.File;
import com.project.skilled_project.domain.file.repository.FileRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final S3Service s3Service;
  private final FileRepository fIleRepository;

  @Override
  @Transactional
  public List<FileResponseDto> upload(Long sourceId, String category, MultipartFile[] files) {
    List<FileResponseDto> fileResponseList = new ArrayList<>();
    // sourceId를 나중에 userId로 변경
    for (MultipartFile file : files) {
      String filePath = s3Service.uploadFile(file, category);
      String orifinalFileName = file.getOriginalFilename();
      File saveFile = new File(sourceId, category, orifinalFileName, filePath);
      fIleRepository.save(saveFile);
      fileResponseList.add(new FileResponseDto(sourceId, category, orifinalFileName, filePath));
    }
    return fileResponseList;
  }

  @Override
  public byte[] download(String fileKey) throws IOException {
    return new byte[0];
  }

  @Override
  public String getFilePath(Long sourceId, String category) {
    File file = fIleRepository.findBySourceIdAndCategory(sourceId, category);
    return file.getFilePath();
  }
}
