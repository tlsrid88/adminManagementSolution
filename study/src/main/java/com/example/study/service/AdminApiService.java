package com.example.study.service;

import com.example.study.model.entity.AdminUser;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.AdminApiRequest;
import com.example.study.model.network.response.AdminApiResponse;

public class AdminApiService extends BaseService<AdminApiRequest, AdminApiResponse, AdminUser> {

    @Override
    public Header<AdminApiResponse> create(Header<AdminApiRequest> req) {
        AdminApiRequest body = req.getData();

        return null;
    }

    @Override
    public Header<AdminApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<AdminApiResponse> update(Header<AdminApiRequest> req) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }
}
