package com.example.study.service;

import com.example.study.model.entity.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartnerApiService extends BaseService<PartnerApiRequest, PartnerApiResponse, Partner> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> req) {

        PartnerApiRequest body = req.getData();

        Partner partner = Partner.builder()
                .partnerNumber(body.getPartnerNumber())
                .address(body.getAddress())
                .businessNumber(body.getBusinessNumber())
                .callCenter(body.getCallCenter())
                .category(categoryRepository.getOne(body.getCategoryId()))
                .ceoName(body.getCeoName())
                .registeredAt(LocalDateTime.now())
                .status(body.getStatus())
                .name(body.getName())
                .build();

        Partner newPartner = baseRepository.save(partner);

        return response(newPartner);
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {

        return baseRepository.findById(id)
                .map(partner -> response(partner))
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않습니다."));
    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> req) {

        PartnerApiRequest body = req.getData();

        return baseRepository.findById(body.getCategoryId())
                .map(partner -> {
                    partner.setRegisteredAt(body.getRegisteredAt())
                            .setCeoName(body.getCeoName())
                            .setBusinessNumber(body.getBusinessNumber())
                            .setCallCenter(body.getCallCenter())
                            .setAddress(body.getAddress())
                            .setStatus(body.getStatus())
                            .setName(body.getName())
                            .setCategory(categoryRepository.getOne(body.getCategoryId()))
                            .setPartnerNumber(body.getPartnerNumber());
                    return partner;
                }).map(partner -> baseRepository.save(partner))
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않습니다."));
    }

    @Override
    public Header delete(Long id) {

        return baseRepository.findById(id)
                .map(partner -> {
                    baseRepository.delete(partner);
                    return partner;
                })
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않습니다."));
    }

    public Header<PartnerApiResponse> response(Partner partner) {
        PartnerApiResponse partnerApiResponse = PartnerApiResponse.builder()
                .partnerNumber(partner.getPartnerNumber())
                .address(partner.getAddress())
                .businessNumber(partner.getBusinessNumber())
                .categoryId(partner.getCategory().getId())
                .callCenter(partner.getCallCenter())
                .ceoName(partner.getCeoName())
                .id(partner.getId())
                .name(partner.getName())
                .registeredAt(partner.getRegisteredAt())
                .unregisteredAt(partner.getUnregisteredAt())
                .status(partner.getStatus())
                .build();

        return Header.OK(partnerApiResponse);
    }

    @Override
    public Header<List<PartnerApiResponse>> search(Pageable pageable) {
        return null;
    }
}
