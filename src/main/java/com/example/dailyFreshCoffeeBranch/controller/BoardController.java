package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.annotation.LoginUser;
import com.example.dailyFreshCoffeeBranch.dto.BoardFormDto;
import com.example.dailyFreshCoffeeBranch.dto.LoginUserDto;
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
     * 공지사항 목록
     *
      * @param model
     * @return
     */
    @GetMapping("/boards")
    public String boards(
            Model model,
            Pageable pageable
    ) {
        model.addAttribute("resultList", boardService.getBoardList(pageable));
        model.addAttribute("boardListCnt", boardService.getBoardListCnt());

        return "board/boardList";
    }

    /**
     * 공지사항 상세
     *
     * @param id
     * @return
     */
    @GetMapping("/boards/{id}")
    public String findBoardByBoardId(
            @PathVariable long id,
            Model model
    ) {
        model.addAttribute("result", boardService.findBoardByBoardId(id));

        return "board/boardDetail";
    }

    /**
     * 공지사항 등록 폼
     *
     * @return
     */
    @GetMapping("/admin/boards/create")
    public String boardForm(Model model) {
        BoardFormDto result = new BoardFormDto();
        model.addAttribute("result", result);

        return "board/boardCreateForm";
    }

    /**
     * 공지사항 수정 폼
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/admin/boards/{id}/update")
    public String boardUpdateForm(@PathVariable Long id, Model model) {
        BoardFormDto result = boardService.findBoardByBoardId(id);
        model.addAttribute("result", result);

        return "board/boardUpdateForm";
    }

    /**
     * 공지사항 등록 처리
     *
     * @param boardFormDto
     * @return
     */
    @PostMapping("/admin/boards/create")
    public String createBoard(
            @Valid BoardFormDto boardFormDto,
            BindingResult bindingResult,
            @LoginUser LoginUserDto loginUserDto,
            Model model
    ) {
        if (bindingResult.hasErrors()) {

            return "board/boardUpdateForm";
        }

        try {

            boardService.createBoard(boardFormDto, loginUserDto.getEmail());

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "board/boardUpdateForm";
        }

        return "redirect:/boards";
    }

    /**
     * 공지사항 수정 처리
     *
     * @param boardFormDto
     * @return
     */
    @PostMapping("/admin/boards/{id}/update")
    public String updateBoard(
            @PathVariable Long id,
            @Valid BoardFormDto boardFormDto,
            BindingResult bindingResult,
            @LoginUser LoginUserDto loginUserDto,
            Model model
    ) {
        if (bindingResult.hasErrors()) {

            return "board/boardUpdateForm";
        }

        try {

            boardService.updateBoard(id, boardFormDto, loginUserDto.getEmail());

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "board/boardUpdateForm";
        }

        return "redirect:/boards/" + id;
    }

    /**
     * 공지사항 삭제 처리
     *
     * @param id
     * @return
     */
    @PostMapping("/admin/boards/{id}/delete")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);

        return "redirect:/boards";
    }

}