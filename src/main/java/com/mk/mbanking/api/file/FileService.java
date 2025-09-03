package com.mk.mbanking.api.file;

import com.mk.mbanking.api.file.Web.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    FileDto uploadSingleFile(MultipartFile file);
    FileDto findByName(String name) throws IOException;
    List<FileDto> uploadMultipleFile(List<MultipartFile> files);
    Resource download(String name);
    void delete(String name);
    
}
