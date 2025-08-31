package com.mk.mbanking.api.file;

import com.mk.mbanking.api.file.Web.FileDto;
import com.mk.mbanking.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{
    private  FileUtil fileUtil;

    @Autowired
    public  void setFileUtil(FileUtil fileUtil){
        this.fileUtil = fileUtil;
    }

    @Override
    public FileDto uploadSingleFile(MultipartFile file) {
        return fileUtil.upload(file);
    }
}