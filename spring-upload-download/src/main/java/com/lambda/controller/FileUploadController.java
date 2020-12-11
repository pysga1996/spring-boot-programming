package com.lambda.controller;

import com.lambda.payload.Response;
import com.lambda.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileUploadController {

  @Autowired
  private FileStorageService fileStorageService;

  @PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
  public Response uploadFile(@RequestParam("file") MultipartFile file) {
    String fileName = fileStorageService.storeFile(file);

    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/downloadFile/")
        .path(fileName)
        .toUriString();

    return new Response(fileName, fileDownloadUri,
        file.getContentType(), file.getSize());
  }

  @PostMapping(value = "/uploadMultipleFiles", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    return Arrays.asList(files)
        .stream()
        .map(file -> uploadFile(file))
        .collect(Collectors.toList());
  }
}
