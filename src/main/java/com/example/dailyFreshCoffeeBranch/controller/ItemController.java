package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.dto.ItemFormDto;
import com.example.dailyFreshCoffeeBranch.dto.ItemSearchFormDto;
import com.example.dailyFreshCoffeeBranch.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;


    /**
     * 상품 목록
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/items")
    public String itemList
    (
            @PageableDefault(size = 9) Pageable pageable,
            @ModelAttribute("searchDto") ItemSearchFormDto searchDto,
            Model model
    ) {
        Page<ItemFormDto> itemPage = itemService.getItemList(pageable, searchDto);
        model.addAttribute("itemPage", itemPage);

        return "item/itemList";
    }

    /**
     * 상품 상세
     *
     * @param id
     * @return
     */
    @GetMapping("/items/{id}")
    public String itemDetail(@PathVariable Long id, Model model) {
        ItemFormDto result = itemService.getItemDetail(id);
        model.addAttribute("result", result);

        return "item/itemDetail";
    }

    /**
     * 상품 폼
     *
     * @param id
     * @param request
     * @param itemFormDto
     * @param model
     * @return
     */
    @GetMapping(value = {"/admin/items/create", "/admin/items/{id}/update"})
    public String itemForm
    (
            @PathVariable(required = false) Long id,
            HttpServletRequest request,
            ItemFormDto itemFormDto,
            Model model
    ) {
        String requestUri = request.getRequestURI();

        if (requestUri.indexOf("/update") > 0) {
            itemFormDto = itemService.getItemDetail(id);
        }

        model.addAttribute("result", itemFormDto);

        return "item/itemForm";
    }

    /**
     * 상품 등록 처리
     *
     * @param dto
     * @param result
     * @return
     */
    @PostMapping("/admin/items/create")
    public String createItem
    (
            @ModelAttribute("itemFormDto") @Valid ItemFormDto dto,
            BindingResult result,
            @RequestParam(name = "imageFileList", required = false) List<MultipartFile> imageFileList,
            Model model
    ) {
        if (result.hasErrors()) {

            return "item/itemForm";
        }


        if (imageFileList.get(0).isEmpty()) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");

            return "item/itemForm";
        }

        try {

            itemService.createItem(dto, imageFileList);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");

            return "item/itemForm";
        }

        return "redirect:/items";
    }

    /**
     * 상품 수정 처리
     *
     * @param id
     * @param itemFormDto
     * @param result
     * @return
     */
    @PostMapping("/admin/items/{id}/update")
    public String updateItem
    (
            @PathVariable Long id,
            @ModelAttribute("itemFormDto") @Valid ItemFormDto itemFormDto,
            BindingResult result,
            @RequestParam(name = "itemImageFile", required = false) List<MultipartFile> multipartFileList,
            Model model
    ) {
        if (result.hasErrors()) {

            return "item/itemForm";
        }

        ItemFormDto itemDetail = itemService.getItemDetail(id);

        if (itemDetail.getImageFileFormDtoList().size() == 0 && multipartFileList.get(0).isEmpty()) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");

            return "item/itemForm";
        }

        try {

            itemService.updateItem(id, itemFormDto, multipartFileList);

        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("errorMessage", "알 수 없는 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");

            return "item/itemForm";
        }

        return "redirect:/items/" + id;
    }

    /**
     * 상품 삭제 처리
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/admin/items/{id}/delete")
    public String deleteItem
    (
            @PathVariable Long id,
            @ModelAttribute("itemFormDto") ItemFormDto itemFormDto
    ) {
        try {

            itemService.deleteItem(id);

        } catch (Exception e) {
            log.error(e.getMessage());

            return "item/itemForm";
        }

        return "redirect:/items";
    }

    /**
     * 상품 삭제 처리
     *
     * @param imageFileId
     * @return
     */
    @DeleteMapping(value = "/admin/imageFiles/{imageFileId}/delete")
    @ResponseBody
    public ResponseEntity<?> deleteImageFile(@PathVariable Long imageFileId) {
        itemService.deleteImageFile(imageFileId);

        return ResponseEntity.ok().body("상품 이미지를 성공적으로 삭제처리하였습니다.");
    }

}