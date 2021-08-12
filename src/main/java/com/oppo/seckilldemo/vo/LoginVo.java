package com.oppo.seckilldemo.vo;

import com.oppo.seckilldemo.annotation.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    private String password;

}
