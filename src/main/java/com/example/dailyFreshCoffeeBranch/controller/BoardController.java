package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.BoardDto;
import com.example.dailyFreshCoffeeBranch.dto.UserInfoDto;
import com.example.dailyFreshCoffeeBranch.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;


    /**
     * 이벤트 목록
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
     * 이벤트 상세
     * @param id
     * @return
     */
    @GetMapping("/boards/{id}")
    public String findBoardByBoardId(@PathVariable long id, Model model) {
        model.addAttribute("boardDto", boardService.findBoardByBoardId(id));
        return "board/boardDetail";
    }

    /**
     * 이벤트 등록 폼
     * @return
     */
    @GetMapping("/admin/boards/create")
    public String boardForm(Model model) {
        BoardDto boardDto = new BoardDto();
        model.addAttribute("boardDto", boardDto);
        return "board/boardCreateForm";
    }

    /**
     * 이벤트 수정 폼
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/admin/boards/{id}/update")
    public String boardUpdateForm(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.findBoardByBoardId(id);
        model.addAttribute("boardDto", boardDto);
        return "board/boardUpdateForm";
    }

    /**
     * 이벤트 등록 처리
     * @param boardDto
     * @return
     */
    @PostMapping("/admin/boards/create")
    public String createBoard(@Valid BoardDto boardDto, BindingResult bindingResult, @LoginUser UserInfoDto userInfoDto, Model model) {
        if (bindingResult.hasErrors()) {
            return "board/boardUpdateForm";
        }

        try {
            boardService.createBoard(boardDto, userInfoDto.getEmail());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/boardUpdateForm";
        }

        return "redirect:/boards";
    }

    /**
     * 이벤트 수정 처리
     * @param boardDto
     * @return
     */
    @PostMapping("/admin/boards/{id}/update")
    public String updateBoard(@PathVariable Long id, @Valid BoardDto boardDto, BindingResult bindingResult, @LoginUser UserInfoDto userInfoDto, Model model) {
        if (bindingResult.hasErrors()) {
            return "board/boardUpdateForm";
        }

        try {
            boardService.updateBoard(id, boardDto, userInfoDto.getEmail());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/boardUpdateForm";
        }

        return "redirect:/boards/" + id;
    }

    /**
     * 이벤트 삭제 처리
     * @param id
     * @return
     */
    @PostMapping("/admin/boards/{id}/delete")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/boards";
    }

}