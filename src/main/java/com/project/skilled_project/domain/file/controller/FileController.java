package com.project.skilled_project.domain.file.controller;

import com.project.skilled_project.domain.file.dto.FileRequestDto;
import com.project.skilled_project.domain.file.dto.FileResponseDto;
import com.project.skilled_project.domain.file.service.FileService;
import com.project.skilled_project.global.response.CommonResponse;
import com.project.skilled_project.global.util.UserDetailsImpl;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

  private final FileService fileService;

  @PostMapping("/upload")
  public ResponseEntity<CommonResponse<List<FileResponseDto>>> uploadFile(
      @RequestPart(value = "category") FileRequestDto fileRequest,
      @RequestPart(value = "files") MultipartFile[] files,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) throws IOException {
    List<FileResponseDto> fileResponseList = fileService.upload(fileRequest.getSourceId(),
        fileRequest.getCategory(), files);
    return CommonResponse.ok(fileResponseList);
  }
}
