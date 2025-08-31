package com.mk.mbanking.api.file;

import com.mk.mbanking.api.file.Web.FileDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileDto uploadSingleFile(MultipartFile file);

}
