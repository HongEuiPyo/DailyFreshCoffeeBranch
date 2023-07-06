package com.example.smallpeopleblog.controller;

import com.example.smallpeopleblog.service.BoardService;
import com.example.smallpeopleblog.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @Value("${uploadPath}")
    private String uploadPath;

    @GetMapping("/uploadForm")
    public String showUploadForm() {
        return "board/uploadFile";
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public ResponseEntity<List<String>> uploadFile(MultipartFile[] files) throws IOException {
        List<String> list = new ArrayList<>();

        for (MultipartFile file : files) {
            System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
            System.out.println("file.getSize() = " + file.getSize());

            File upFile = new File(uploadPath, file.getOriginalFilename());
            file.transferTo(upFile); // 업로드된 파일을 경로에 저장
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(String filename) {
        System.out.println("filename = " + filename);
        Resource resource = new FileSystemResource(uploadPath + filename);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(String filename) {
        System.out.println("filename = " + filename);

        File file = new File(uploadPath + filename);
        if (file.delete() == true)
            return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 공지사항 목록
      * @param model
     * @return
     */
    @GetMapping("/boards")
    public String boards(Model model, Pageable pageable) {
        model.addAttribute("boardList", boardService.getBoardList(pageable));
        model.addAttribute("boardListCnt", boardService.getBoardListCnt());
        return "board/boardList";
    }

    /**
     * 공지사항 상세
     * @param id
     * @return
     */
    @GetMapping("/boards/{id}")
    public String findBoardByBoardId(@PathVariable long id, Model model) {
        model.addAttribute("boardDto", boardService.findBoardByBoardId(id));
        return "board/boardDetail";
    }

    /**
     * 공지사항 폼 페이지
     * @return
     */
    @GetMapping(value = {"/admin/boards/create", "/admin/boards/{id}/update"})
    public String boardForm(HttpServletRequest request, @PathVariable(required = false) Long id, BoardDto boardDto, Model model) {
        String requestUri = request.getRequestURI();
        if (requestUri.indexOf("/update") > 0) {
            boardDto = boardService.findBoardByBoardId(id);
        }
        model.addAttribute("boardDto", boardDto);
        return "board/boardForm";
    }

    /**
     * 공지사항 등록
     * @param boardDto
     * @return
     */
    @PostMapping("/admin/boards/create")
    public String createBoard(@Valid BoardDto boardDto, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "board/boardForm";
        }

        try {
            String email = principal.getName();
            boardService.createBoard(boardDto, email);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/boardForm";
        }

        return "redirect:/boards";
    }

    /**
     * 공지사항 수정
     * @param boardDto
     * @return
     */
    @PostMapping("/admin/boards/{id}/update")
    public String updateBoard(@PathVariable Long id, @Valid BoardDto boardDto, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "board/boardForm";
        }

        try {
            boardService.updateBoard(id, boardDto, principal.getName());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/boardForm";
        }

        return "redirect:/boards/" + id;
    }

    /**
     * 공지사항 삭제
     * @param id
     * @return
     */
    @PostMapping("/admin/boards/{id}/delete")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/boards";
    }
}