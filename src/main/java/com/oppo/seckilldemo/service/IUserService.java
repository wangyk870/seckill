package com.oppo.seckilldemo.service;

import com.oppo.seckilldemo.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oppo.seckilldemo.vo.LoginVo;
import com.oppo.seckilldemo.vo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-11
 */
public interface IUserService extends IService<User> {
    RespBean doLogin(LoginVo loginVo);
}
