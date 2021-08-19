package com.oppo.seckilldemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oppo.seckilldemo.pojo.Order;
import com.oppo.seckilldemo.pojo.SeckillOrder;
import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.service.IGoodsService;
import com.oppo.seckilldemo.service.IOrderService;
import com.oppo.seckilldemo.service.ISeckillOrderService;
import com.oppo.seckilldemo.vo.GoodsVo;
import com.oppo.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    IGoodsService goodsService;
    @Autowired
    ISeckillOrderService seckillOrderService;
    @Autowired
    IOrderService orderService;

    @RequestMapping("/doSeckill")
    @ResponseBody
    public JSONObject doSeckill(Long goodsId, User user){
        JSONObject json = new JSONObject();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if(goodsVo.getStockCount() < 1){
            json.put("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return json;
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId()).eq("goods_id", goodsId));
        if(seckillOrder != null){
            json.put("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return json;
        }
        Order order = orderService.seckill(user, goodsVo);
        json.put("order", order);
        json.put("goods", goodsVo);
        return json;
    }

}
