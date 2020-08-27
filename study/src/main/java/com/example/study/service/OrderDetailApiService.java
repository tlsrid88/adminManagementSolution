package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetailApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderGroupRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailApiService extends BaseService<OrderDetailApiRequest, OrderDetailApiResponse, OrderDetail> {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> req) {
        OrderDetailApiRequest body = req.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .arrivalDate(body.getArrivalDate())
                .orderGroup(orderGroupRepository.getOne(body.getOrderGroupId()))
                .item(itemRepository.getOne(body.getItemId()))
                .quantity(body.getQuantity())
                .status(body.getStatus())
                .totalPrice(body.getTotalPrice())
                .build();

        OrderDetail newOrderDetail = baseRepository.save(orderDetail);

        return response(orderDetail);
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않습니다."));
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> req) {

        OrderDetailApiRequest body = req.getData();

        Optional<OrderDetail> optional = baseRepository.findById(body.getId());

        return optional.map(orderDetail -> {
                    orderDetail.setTotalPrice(body.getTotalPrice())
                            .setQuantity(body.getQuantity())
                            .setArrivalDate(body.getArrivalDate())
                            .setStatus(body.getStatus())
                            .setItem(itemRepository.getOne(body.getItemId()))
                            .setOrderGroup(orderGroupRepository.getOne(body.getOrderGroupId()));

                    return  orderDetail;
                })
                .map(orderDetail -> baseRepository.save(orderDetail))
                .map(orderDetail -> response(orderDetail))
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않습니다."));

    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(orderDetail -> {baseRepository.delete(orderDetail);
                return Header.OK();})
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않습니다."));
    }

    public Header<OrderDetailApiResponse> response(OrderDetail orderDetail) {

        OrderDetailApiResponse body = OrderDetailApiResponse.builder()
                .id(orderDetail.getId())
                .arrivalDate(orderDetail.getArrivalDate())
                .itemId(orderDetail.getItem().getId())
                .orderGroupId(orderDetail.getOrderGroup().getId())
                .quantity(orderDetail.getQuantity())
                .status(orderDetail.getStatus())
                .totalPrice(orderDetail.getTotalPrice())
                .build();

        return Header.OK(body);
    }

    @Override
    public Header<List<OrderDetailApiResponse>> search(Pageable pageable) {
        return null;
    }
}
