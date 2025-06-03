package com.ino.async.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Component
public class FileUtil {

    public Map<String, String> fileupload(String folderName, MultipartFile file) {

        String filePath = "/upload/" + folderName + DateTimeFormatter.ofPattern("/yyyyMMdd").format(LocalDate.now());
        File filePathDir = new File("C:" + filePath);
        if(!filePathDir.exists()){   // 해당 경로의 폴더가 존재하지 않을 경우
            filePathDir.mkdirs();    // 해당 폴더 만들기
        }

        String originalFilename = file.getOriginalFilename(); // "abc.def.jpg"
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".")); // ".jpg"
        String filesystemName = UUID.randomUUID().toString().replace("-", "") + ext; // UUID.randomUUID() : 랜덤값발생 (32자리 + - 4개)

        try {
            file.transferTo(new File(filePathDir, filesystemName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> map = Map.of(
                "filePath", filePath,
                "originalFilename", originalFilename,
                "filesystemName", filesystemName
        );

        return map;
    }

}
