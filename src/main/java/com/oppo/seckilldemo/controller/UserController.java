package com.oppo.seckilldemo.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.service.IUserService;
import com.oppo.seckilldemo.utils.FileUtil;
import com.oppo.seckilldemo.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * <p>
 *  接口涉及修改数据库，和JMeter测试配置文件，慎用！！
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-11
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    OkHttpUtil okHttpUtil;


    @RequestMapping("/findUser")
    @ResponseBody
    public JSONObject findUser(User user){
        JSONObject json = new JSONObject();
        User user1 = userService.getOne(new QueryWrapper<>(user));
        json.put(user.getNickname(),user1.getId());
        return json;
    }

    //@RequestMapping("/userGenerator")
    //@ResponseBody
    public JSONObject userGenerator(int count){
        JSONObject json = new JSONObject();
        User user = new User();
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        user.setSalt("1a2b3c4d");
        user.setRegisterDate(new Date());
        user.setLoginCount(0);
        for(int i=1; i<=count; i++){
            user.setId(18253660000L + i);
            user.setNickname("test" + i);
            if(userService.save(user)){
                json.put(""+i, user.getId());
            }
        }
        return json;
    }

    //@RequestMapping("/userConfigGenerator")
    //@ResponseBody
    public JSONObject userConfigGenerator(){
        String url = "http://localhost:8080/login/dologin";
        JSONObject json = new JSONObject();
        List<User> userList = userService.list();
        List<Long> list = new ArrayList<Long>();
        for(User user : userList){
            if(user.getId() > 18253657808L){
                list.add(user.getId());
            }
        }
        log.info("待登录人数：" + list.size());
        for (int i=0; i< list.size(); i++){

            Map<String, String> params = new HashMap<>();
            params.put("mobile",""+list.get(i));
            params.put("password","123456");
            String result = okHttpUtil.doPost(url,params);
            log.info(result);
            JSONObject jsonOne = JSONObject.parseObject(result);
            JSONObject jsonTwo = (JSONObject) jsonOne.get("resultMap");
            String token = jsonTwo.getString("token");
            //String token = (String)JSONObject.parseObject((String)jsonOne.get("resultMap")).get("token");
            String write = list.get(i) + "," + token;
            try{
                FileUtil.writeInToFile("D:\\System\\apache-jmeter-5.4.1\\api_config_yk\\test.txt", write + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            json.put(""+i, write);
        }

//        for (int i=0; i< list.size(); i++){
//            log.info(""+list.get(i));
//            Map<String, String> params = new HashMap<>();
//            params.put("mobile",""+list.get(i));
//            params.put("password","123456");
//            json.put(""+i, params);
//        }

        return json;
    }

    //@RequestMapping("/testHttp")
    //@ResponseBody
    public JSONObject testOkHttp(String url){
        Map<String, String> params = new HashMap<>();
        Map<String, String> header = new HashMap<>();
        header.put("token", "3ce297fef82f47d2afeb2520a430b7cc");
        String result = okHttpUtil.doPost(url, params, header);
        log.info(result);
        JSONObject json = JSONObject.parseObject(result);
        log.info(json.toJSONString());
        return json;
    }

}
