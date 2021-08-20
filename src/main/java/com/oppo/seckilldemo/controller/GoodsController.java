package com.oppo.seckilldemo.controller;


import com.alibaba.fastjson.JSONObject;
import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.service.IGoodsService;
import com.oppo.seckilldemo.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-18
 */
@Slf4j
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

    @RequestMapping("/toList")
    @ResponseBody
    public JSONObject toList(){
        JSONObject json = new JSONObject();
        json.put("goodsList", goodsService.findGoodsVo());
        return json;
    }

    @RequestMapping("/toDetail")
    @ResponseBody
    public JSONObject toDetail(Long goodsId){
        log.info("goodsId is :" + goodsId);
        //秒杀状态
        int seckillStatus = 0;
        //秒杀倒计时 s
        int remainSeconds = 0;
        JSONObject json = new JSONObject();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        if(nowDate.before(startDate)){
            //秒杀前
            remainSeconds = ((int)(startDate.getTime() - nowDate.getTime())/1000);
        }else if(nowDate.after(endDate)){
            //秒杀结束
            seckillStatus = 2;
            remainSeconds = -1;
        }else{
            //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        json.put("goods", goodsVo);
        json.put("seckillStatus", seckillStatus);
        json.put("remainSeconds",remainSeconds);

        return json;
    }

}
