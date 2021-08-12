package com.oppo.seckilldemo.controller;

import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.service.IUserService;
import com.oppo.seckilldemo.utils.StringUtil;
import com.oppo.seckilldemo.utils.ValidatorUtil;
import com.oppo.seckilldemo.vo.LoginVo;
import com.oppo.seckilldemo.vo.RespBean;
import com.oppo.seckilldemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/dologin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo){
        log.info("{}" + loginVo);
        return userService.doLogin(loginVo);
    }

}
