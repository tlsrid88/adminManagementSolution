package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetailApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailController extends CrudController<OrderDetailApiRequest, OrderDetailApiResponse, OrderDetail> {

}
