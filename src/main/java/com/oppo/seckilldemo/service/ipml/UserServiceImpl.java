package com.oppo.seckilldemo.service.ipml;

import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.mapper.UserMapper;
import com.oppo.seckilldemo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
