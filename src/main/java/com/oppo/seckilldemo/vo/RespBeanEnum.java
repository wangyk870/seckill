package com.oppo.seckilldemo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    SUCCESS(200, "SECCESS"),
    ERROR(500, "服务器端异常");

}
