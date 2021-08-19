package com.oppo.seckilldemo.interceptor;

import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.utils.StringUtil;
import com.oppo.seckilldemo.vo.CommonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        Date date = new Date();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String token = request.getHeader("token");
        log.info(token);
        if(StringUtil.isEmpty(token)){
            log.info("用户未登录");
            response.getWriter().print("用户未登录");
            return false;
        }
        // String username = CommonVo.userInfo.get(token);
        User user = (User)redisTemplate.opsForValue().get(token);

        if(null == user){
            log.info("token不正确，请重新登录");
            response.getWriter().print("token不正确，请重新登录");
            return false;
        }

        log.info("【登录信息】：" + user.getNickname());
        log.info("【请求连接】：" + request.getRequestURI());
        log.info("【请求时间】：" + sdf.format(date));

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
