package com.mk.mbanking.util;

import com.mk.mbanking.api.file.Web.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {
    @Value("${file.base-url}")
    private String fileBaseUrl;
    @Value("${file.server-path}")
    private String fileServerPath;
    @Value("${file.download-url}")
    private String fileDownload;

   public FileDto upload(MultipartFile file){
       String extension = getExtension(file.getOriginalFilename());
       String name = String.format("%s.%s", UUID.randomUUID(),extension);
       Long size = file.getSize();
       String url = getUrl(name);

       Path path = Paths.get(fileServerPath+name);
       try {
           Files.copy(file.getInputStream(),path);
       } catch (IOException e) {
         throw  new ResponseStatusException(
                 HttpStatus.INTERNAL_SERVER_ERROR,
                 e.getMessage());
       }
       return FileDto.builder()
               .name(name)
               .size(size)
               .extension(extension)
               .url(url)
               .downloadUrl(fileDownload+ name)
               .build();
   }
   public String getExtension(String name){
       int dotLastIndex = name.lastIndexOf(".");
       return name.substring(dotLastIndex+1);
   }
   public String getUrl(String name){
      return fileBaseUrl + name;
   }
   public String getDownloadUrl(String name){
       return fileDownload + name;
   }
    
}
