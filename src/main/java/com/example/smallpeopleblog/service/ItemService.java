package com.example.smallpeopleblog.service;

import com.example.smallpeopleblog.dto.ItemDto;
import com.example.smallpeopleblog.entity.ImageFile;
import com.example.smallpeopleblog.entity.Item;
import com.example.smallpeopleblog.exception.ImageFileNotFoundException;
import com.example.smallpeopleblog.exception.ItemNotFoundException;
import com.example.smallpeopleblog.repository.ImageFileRepository;
import com.example.smallpeopleblog.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ImageFileRepository imageFileRepository;
    private final FileService fileService;

    /**
     * 상품 목록
     * @param pageable
     * @return
     */
    public Page<ItemDto> getItemList(Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(Item::toDto);
    }

    /**
     * 상품 상세
     * @param id
     * @return
     */
    public ItemDto getItemDetail(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("상품을 확인할 수 없습니다."));
        return item.toDto();
    }

    /**
     * 상품 등록
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void createItem(ItemDto dto, List<MultipartFile> multipartFileList) throws Exception {
        // 1. 상품 등록
        Item item = dto.toEntity();
        itemRepository.save(item);

        // 2. 이미지 파일 등록
        for (MultipartFile file : multipartFileList) {
            String originalImageName = file.getOriginalFilename();
            String imageName = "";
            String imageUrl = "";
            String savedFileName = "";

            if (!originalImageName.isEmpty()) {
                savedFileName = fileService.uploadFile(file); // 파일 업로드
                imageName = originalImageName.substring(0, originalImageName.indexOf("."));
                imageUrl = "/images/" + savedFileName;
            }

            ImageFile imageFile = ImageFile.builder()
                    .imageName(imageName)
                    .originalImageName(originalImageName)
                    .imageUrl(imageUrl)
                    .item(item)
                    .savedFileName(savedFileName)
                    .build();

            imageFileRepository.save(imageFile);
        }
    }

    /**
     * 상품 삭제
     * @param itemId
     * @throws Exception
     */
    @Transactional
    public void deleteItem(Long itemId) {
        List<ImageFile> imageFileList = imageFileRepository.findAllByItemId(itemId);

        // 1. 이미지첨부파일 삭제
        for (int i=0; i<imageFileList.size(); i++) {
            String savedFileName = imageFileList.get(i).getSavedFileName();
            fileService.deleteFile(savedFileName);
        }

        // 2. 이미지 파일 삭제
        imageFileRepository.deleteByItemId(itemId);

        // 3. 상품 삭제
        itemRepository.deleteById(itemId);
    }

    /**
     * 상품 수정 처리
     * @param itemId
     * @param itemDto
     * @param multipartFileList
     * @throws Exception
     */
    @Transactional
    public void updateItem(Long itemId, ItemDto itemDto, List<MultipartFile> multipartFileList) throws Exception {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품을 찾을 수 없습니다."));

        for (MultipartFile file : multipartFileList) {
            String originalImageName = file.getOriginalFilename();
            String imageName = "";
            String imageUrl = "";
            String savedFileName = "";

            if (!originalImageName.isEmpty()) {
                savedFileName = fileService.uploadFile(file); // 파일 업로드
                imageName = originalImageName.substring(0, originalImageName.indexOf("."));
                imageUrl = "/images/" + savedFileName;

                ImageFile imageFile = ImageFile.builder()
                        .imageName(imageName)
                        .originalImageName(originalImageName)
                        .imageUrl(imageUrl)
                        .item(item)
                        .savedFileName(savedFileName)
                        .build();

                imageFileRepository.save(imageFile);
            }
        }

        item.update(itemDto);
    }

    @Transactional
    public void deleteImageFile(Long imageFileId) {
        ImageFile imageFile = imageFileRepository.findById(imageFileId)
                .orElseThrow(() -> new ImageFileNotFoundException("이미지 파일을 찾을 수 없습니다."));

        fileService.deleteFile(imageFile.getSavedFileName());

        imageFileRepository.deleteById(imageFile.getId());
    }
}