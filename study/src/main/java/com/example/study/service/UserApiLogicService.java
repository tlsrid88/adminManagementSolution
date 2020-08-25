package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Builder
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    // create 하는 과정을 설명을 해보면
    // 1. request받아준다.
    // 2. request에 맞는 data 생성
    // 3. 생성된 데이터를 return한다.
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        User user  = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .email(userApiRequest.getEmail())
                .phoneNumber(userApiRequest.getPhoneNumber())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = baseRepository.save(user);

        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        // id -> Repository를 통해 ->  getOne, getById
        // user -> userApiResponse

        Optional<User> optional = baseRepository.findById(id);

        return optional
                .map(user -> response(user))
                .orElseGet(() -> Header.ERROR("데이터가 없습니다."));
    }

    @Override
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {

        // 1. id 확인
        UserApiRequest userApiRequest = request.getData();

        // 2. id와 맞는 데이터
        Optional<User> optional = baseRepository.findById(userApiRequest.getId());

        return optional.map(user -> {
                user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(UserStatus.REGISTERED)
                    .setEmail(userApiRequest.getEmail())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());

                return user;
                })
                .map(user -> baseRepository.save(user)) // update
                .map(user -> response(user)) //
                .orElseGet(() -> Header.ERROR("데이터 오류"));

    }

    @Override
    public Header delete(Long id) {

        // 1. id -> search
        Optional<User> optional = baseRepository.findById(id);
        // 2. delete
        return optional.map(user -> {
            baseRepository.delete(user);
            return Header.OK();
            }).orElseGet(() -> Header.ERROR("데이터가 존재하지 않습니다."));

        // 3. return

    }

    private Header<UserApiResponse> response(User user) {

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword()) // TODO : 암호화 작업을 수행해야한다.
                .status(user.getStatus())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        // Head.ok() + UserApiResponse
        return Header.OK(userApiResponse);
    }
}
