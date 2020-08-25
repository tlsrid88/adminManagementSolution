package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.Partner;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/partner")
public class PartnerController extends CrudController<PartnerApiRequest, PartnerApiResponse, Partner> {

}
