package com.project.skilled_project.domain.file.controller;

import com.project.skilled_project.domain.file.dto.FileRequestDto;
import com.project.skilled_project.domain.file.dto.FileResponseDto;
import com.project.skilled_project.domain.file.service.FileService;
import com.project.skilled_project.global.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

  private final FileService fileService;

  @PostMapping("/upload")
  public ResponseEntity<CommonResponse<List<FileResponseDto>>> uploadFile(
      @ModelAttribute FileRequestDto fileRequest
  ) throws IOException {
    List<FileResponseDto> fileResponseList = fileService.upload(fileRequest.getSourceId(),
        fileRequest.getCategory(), fileRequest.getFiles());
    return CommonResponse.ok(fileResponseList);
  }

  @GetMapping("/download")
  public ResponseEntity<CommonResponse<String>> downloadFile(
      @RequestParam(name = "file_path") String filePath,
      HttpServletResponse response
  ) throws IOException {
    String fileContent = fileService.download(filePath);
    response.setContentType(String.valueOf(MediaType.APPLICATION_OCTET_STREAM));
    return CommonResponse.ok(fileContent);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<CommonResponse<Void>> deleteFile(
      @RequestParam(name = "file_path") String filePath
  ) {
    fileService.delete(filePath);
    return CommonResponse.ok(null);
  }
}
