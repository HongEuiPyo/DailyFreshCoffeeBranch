package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.AddressDto;
import com.example.dailyFreshCoffeeBranch.dto.ItemDto;
import com.example.dailyFreshCoffeeBranch.entity.Address;
import com.example.dailyFreshCoffeeBranch.entity.Item;
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


    public List<ItemDto> getTop3ItemList() {
        List<Item> itemList =  itemRepository.getTop3ItemList();
        return itemList.stream()
                .map(item -> item.toDto())
                .collect(Collectors.toList());
    }

    public AddressDto getStoreLocation() {
        Address storeLocation = addressRepository.findStoreLocation();
        return AddressDto.of(storeLocation);
    }

}