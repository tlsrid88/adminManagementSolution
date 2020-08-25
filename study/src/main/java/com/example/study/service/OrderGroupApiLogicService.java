package com.example.study.service;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderGroupApiLogicService
        extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> req) {
        OrderGroupApiRequest body = req.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .status(body.getStatus())
                .orderType(body.getOrderType())
                .revAddress(body.getRevAddress())
                .revName(body.getRevName())
                .paymentType(body.getPaymentType())
                .totalPrice(body.getTotalPrice())
                .totalQuantity(body.getTotalQuantity())
                .orderAt(LocalDateTime.now())
                .arrivalDate(LocalDateTime.now().plusDays(2))
                // getOne의 타입은 어떻게 되는지 확인을 해야한다. fincById로 하니까 return이 Optinal이여서 User타입과 매치되지 않
                // 았습니다.
                .user(userRepository.getOne(body.getUserId()))
                .build();

        OrderGroup newOrderGroup = baseRepository.save(orderGroup);

        return response(newOrderGroup);
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {

        Optional<OrderGroup> optional = baseRepository.findById(id);

        return baseRepository.findById(id)
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터가 없습니다."));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> req) {
        OrderGroupApiRequest body = req.getData();

        return baseRepository.findById(body.getId())
                .map(orderGroup -> {
                    orderGroup
                            .setOrderType(body.getOrderType())
                            .setRevAddress(body.getRevAddress())
                            .setRevName(body.getRevName())
                            .setPaymentType(body.getPaymentType())
                            .setTotalPrice(body.getTotalPrice())
                            .setTotalQuantity(body.getTotalQuantity())
                            .setOrderAt(body.getOrderAt())
                            .setArrivalDate(body.getArrivalData())
                            .setUser(userRepository.getOne(body.getId()));
                    return orderGroup;
                })
                .map(updateOrderGroupE -> baseRepository.save(updateOrderGroupE))
                .map(resOrderGroupH -> response(resOrderGroupH))
                .orElseGet(() -> Header.ERROR("데이터가 없습니다."));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderGroup> body = baseRepository.findById(id);

        return body.map(user -> {
            baseRepository.delete(user);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터가 없습니다."));
    }

    private Header<OrderGroupApiResponse> response(OrderGroup orderGroup) {

        OrderGroupApiResponse orderGroupApiResponse = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
                .arrivalData(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId()) // 여기 체크 한 번 해보자.
                .build();

        return Header.OK(orderGroupApiResponse);
    }
}
