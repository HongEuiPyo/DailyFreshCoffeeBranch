package com.example.smallpeopleblog.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RequestMapping("/com/file")
@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileService fileService;

    @Value("${itemImgLocation}")
    private String uploadPath;

    /**
     * 파일 다운로드
     * @param savedFileName
     * @return
     */
    @GetMapping(value = "/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestParam("savedFileName") String savedFileName) {
        Resource resource = new FileSystemResource(uploadPath + "/"  + savedFileName);

        if (resource.exists()) {
            String fileName = resource.getFilename();
            fileName = fileName.substring(fileName.indexOf("_") + 1); // 다운로드 파일명

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment()
                            .filename(fileName, StandardCharsets.UTF_8)
                            .build()
                            .toString()
                    )
                    .body(resource);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 파일 삭제
     * @param requestParamMap
     * @return
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteFile(@RequestBody Map<String, String> requestParamMap) {
        String originalFileName = requestParamMap.get("originalFileName");
        fileService.deleteFile(originalFileName);
        return ResponseEntity.ok().body("파일을 삭제하였습니다.");
    }

}