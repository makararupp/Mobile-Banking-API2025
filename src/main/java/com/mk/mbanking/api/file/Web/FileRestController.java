package com.mk.mbanking.api.file.Web;

import com.mk.mbanking.api.file.FileService;
import com.mk.mbanking.base.BaseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileRestController {
    private final FileService fileService;

    @PostMapping
    public BaseApi<?> uploadSingleFile(@RequestPart("file")MultipartFile file){
        FileDto fileDto = fileService.uploadSingleFile(file);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("file has been uploaded.")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }
    @GetMapping("/{name}")
    public BaseApi<?> findByName(@PathVariable String name) throws IOException{
        FileDto dto = fileService.findByName(name);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("file name has been found")
                .timestamp(LocalDateTime.now())
                .data(dto)
                .build();
    }
    @PostMapping("/multiple")
    public BaseApi<?> uploadMultipleFile(@RequestPart("files")List<MultipartFile> files){
        List<FileDto> fileDto = fileService.uploadMultipleFile(files);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Files have been uploaded")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }
}
