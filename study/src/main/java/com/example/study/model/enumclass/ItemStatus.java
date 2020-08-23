package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemStatus {
    REGISTERED(1,"등록","상태 등록"),
    UNREGISTERED(2,"해지","상태 해지"),
    WAITING(2, "대기","상태 대기");

    private Integer id;
    private String title;
    private String descriptions;
}
