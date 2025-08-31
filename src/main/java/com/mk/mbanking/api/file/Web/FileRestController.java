package com.mk.mbanking.api.file.Web;

import com.mk.mbanking.api.file.FileService;
import com.mk.mbanking.base.BaseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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
}
