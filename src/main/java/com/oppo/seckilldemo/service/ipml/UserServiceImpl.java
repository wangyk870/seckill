package com.oppo.seckilldemo.service.ipml;

import com.oppo.seckilldemo.exception.GlobalException;
import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.mapper.UserMapper;
import com.oppo.seckilldemo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oppo.seckilldemo.utils.MD5Util;
import com.oppo.seckilldemo.utils.StringUtil;
import com.oppo.seckilldemo.utils.UUIDUtil;
import com.oppo.seckilldemo.utils.ValidatorUtil;
import com.oppo.seckilldemo.vo.CommonVo;
import com.oppo.seckilldemo.vo.LoginVo;
import com.oppo.seckilldemo.vo.RespBean;
import com.oppo.seckilldemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-11
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

//        if(StringUtil.isEmpty(mobile) || StringUtil.isEmpty(password)){
//            log.info("校验：用户名或密码为空登陆失败");
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//
//        if(!ValidatorUtil.isMobile(mobile)){
//            log.info("校验：手机号格式问题登陆失败");
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }

        User user = userMapper.selectById(mobile);
        if(null == user){
            log.info("校验：找不到用户");
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if(!MD5Util.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            log.info(MD5Util.fromPassToDBPass(password,user.getSalt()) + "?=" + user.getPassword());
            log.info("校验：密码");
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

        String token = UUIDUtil.uuid();
        log.info(token);
        //CommonVo.userInfo.put(token, mobile);
        redisTemplate.opsForValue().set(token, user);
        HashMap<String, String> result = new HashMap<>();
        result.put("token", token);
        return RespBean.success(result);
    }
}
