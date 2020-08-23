package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderType {

    ALL(0,"묶음", "모두 한 번에 전송하겠음"),
    EACH(1,"개별","모든 상품을 준비되는 대로 전송")
    ;

    private Integer id;
    private String title;
    private String descriptions;
}
