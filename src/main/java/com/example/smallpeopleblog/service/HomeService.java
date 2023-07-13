package com.example.smallpeopleblog.service;

import com.example.smallpeopleblog.dto.ItemDto;
import com.example.smallpeopleblog.entity.Item;
import com.example.smallpeopleblog.repository.ItemQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeService {

    private final ItemQueryDslRepository itemQueryDslRepository;


    public List<ItemDto> getTop3ItemList() {
        List<Item> itemList =  itemQueryDslRepository.getTop3ItemList();
        return itemList.stream()
                .map(item -> item.toDto())
                .collect(Collectors.toList());
    }

}