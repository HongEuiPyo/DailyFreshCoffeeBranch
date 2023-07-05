package com.example.smallpeopleblog.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${itemImgLocation}")
    private String uploadPath;

    /**
     * 파일 업로드
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception {
        // 1. UUID 설정
        UUID uuid = UUID.randomUUID();

        // 2. 파일 명, 확장자, 저장 경로 설정
        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName.substring(0, originalFileName.indexOf("."));
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid + "_" + fileName + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // 3. 파일 저장
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(file.getBytes());
        fos.close();

        return savedFileName;
    }

    /**
     * 파일 삭제
     * @param originalFileName
     * @return
     * @throws Exception
     */
    public Map<String, String> deleteFile(String originalFileName) {

        Map<String, String> resultMap = new HashMap<>();

        if (StringUtils.hasText(originalFileName)) {

            try {
                System.out.println("uploadPath + file.getOriginalFilename() = " + uploadPath + originalFileName);
                Path filePath = Paths.get(uploadPath + originalFileName);
                Files.deleteIfExists(filePath);
            } catch (NoSuchFileException e) {
                resultMap.put("eMessage", "삭제하려는 파일이 없습니다.");
            } catch (IOException e) {
                resultMap.put("eMessage", "알 수 없는 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
                log.error(e.getMessage());
            }

        }

        return resultMap;
    }

}