package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.enumclass.ItemStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> req) {

        ItemApiRequest body = req.getData();

        Item item = Item.builder()
                .status(ItemStatus.REGISTERED)
                .name(body.getName())
                .title(body.getTitle())
                .content(body.getContent())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);

        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {

        Optional<Item> optional  = baseRepository.findById(id);

        return optional
                .map(item -> response(item))
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> req) {
        ItemApiRequest body = req.getData();

        Item item = Item.builder()
                .id(body.getId())
                .status(body.getStatus())
                .name(body.getName())
                .title(body.getTitle())
                .content(body.getContent())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);

        return response(newItem);
    }

    @Override
    public Header delete(Long id) {

        Optional<Item> optional = baseRepository.findById(id);

        return optional.map(item -> {
            baseRepository.delete(item);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터가 존재하지 않습니다"));

    }

    private Header<ItemApiResponse> response(Item item) {

        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return Header.OK(body);
    }


}
