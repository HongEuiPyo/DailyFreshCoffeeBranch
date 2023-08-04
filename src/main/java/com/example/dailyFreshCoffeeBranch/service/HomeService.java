package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.AddressResponseDto;
import com.example.dailyFreshCoffeeBranch.dto.ItemFormDto;
import com.example.dailyFreshCoffeeBranch.domain.Item;
import com.example.dailyFreshCoffeeBranch.exception.AddressNotFoundException;
import com.example.dailyFreshCoffeeBranch.repository.AddressRepository;
import com.example.dailyFreshCoffeeBranch.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeService {

    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;


    public List<ItemFormDto> getTop3ItemList() {
        List<Item> itemList =  itemRepository.getTop3ItemList();
        return itemList.stream()
                .map(item -> item.toDto())
                .collect(Collectors.toList());
    }

    public AddressResponseDto getStoreLocation() {
        AddressResponseDto storeLocation = addressRepository.findStoreLocation()
                .orElseThrow(() -> new AddressNotFoundException("매장 위치를 찾을 수 없습니다."));
        return storeLocation;
    }

}