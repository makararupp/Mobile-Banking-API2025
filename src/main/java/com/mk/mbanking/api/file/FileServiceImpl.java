package com.mk.mbanking.api.file;

import com.mk.mbanking.api.file.Web.FileDto;
import com.mk.mbanking.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public FileDto findByName(String name) throws IOException {
        Resource resource = fileUtil.load(name);

        if(resource.exists()){
            return FileDto.builder()
                    .name(resource.getFilename())
                    .extension(fileUtil.getExtension(resource.getFilename()))
                    .size(resource.contentLength())
                    .readableSize(fileUtil.readableFileSize(resource.contentLength()))
                    .url(fileUtil.getUrl(resource.getFilename()))
                    .downloadUrl(fileUtil.getDownloadUrl(resource.getFilename()))
                    .build();
        }

        throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "File is not found");
    }

    @Override
    public List<FileDto> uploadMultipleFile(List<MultipartFile> files) {
        List<FileDto> fileDtoList = new ArrayList<>();

        for (MultipartFile file : files){
            FileDto fileDto = fileUtil.upload(file);
            fileDtoList.add(fileDto);
        }
        return fileDtoList;
    }
}