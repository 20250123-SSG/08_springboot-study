package com.kyungbae.app.util;

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

    /**
     * 파일을 새 경로에 저장한 뒤 경로 및 이름 반환
     * @param folderName    생성할 폴더 이름
     * @param file          저장할 MultipartFile
     * @return  filePath, originalFileName, filesystemName
     */
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
                "originalFileName", originalFilename,
                "filesystemName", filesystemName
        );

        return map;
    }

}
