package com.example.study.ifs;

import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Header<Req> req); // TODO: request Object 추가

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> req);

    Header delete(Long id);
}
