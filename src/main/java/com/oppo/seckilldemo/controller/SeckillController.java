package com.oppo.seckilldemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.oppo.seckilldemo.pojo.Order;
import com.oppo.seckilldemo.pojo.SeckillMessage;
import com.oppo.seckilldemo.pojo.SeckillOrder;
import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.rabbitmq.MQSender;
import com.oppo.seckilldemo.service.IGoodsService;
import com.oppo.seckilldemo.service.IOrderService;
import com.oppo.seckilldemo.service.ISeckillOrderService;
import com.oppo.seckilldemo.vo.GoodsVo;
import com.oppo.seckilldemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    IGoodsService goodsService;
    @Autowired
    ISeckillOrderService seckillOrderService;
    @Autowired
    IOrderService orderService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MQSender mqSender;

    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();

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
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
//                .eq("user_id", user.getId()).eq("goods_id", goodsId));
        SeckillOrder seckillOrder = (SeckillOrder)redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsVo.getId());
        if(seckillOrder != null){
            json.put("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return json;
        }
        Order order = orderService.seckill(user, goodsVo);
        json.put("order", order);
        json.put("goods", goodsVo);
        return json;
    }

    @RequestMapping("/doSeckillByRedis")
    @ResponseBody
    public JSONObject doSeckillByRedis(Long goodsId, User user){
        JSONObject json = new JSONObject();
        //GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder)redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsId);
        if(seckillOrder != null){
            json.put("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return json;
        }
        //通过内存标记是否有库存，没有直接返回，不走Redis也不走数据库
        if(EmptyStockMap.get(goodsId)){
            json.put("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return json;
        }
        //预减库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        //判断库存
        if(stock < 0){
            EmptyStockMap.put(goodsId, true);
            valueOperations.increment("seckillGoods:" + goodsId);
            json.put("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return json;
        }
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JSONObject.toJSONString(seckillMessage));
        json.put("msg", "正在排队中");
        return json;
    }

    @RequestMapping("/result")
    @ResponseBody
    public JSONObject getSeckillResult(User user, Long goodsId){
        JSONObject json = new JSONObject();
        Long orderId = seckillOrderService.getResult(user, goodsId);
        json.put("msg", orderId);
        return json;
    }

    //初始化，把商品库存数量加载到Redis，库存状态加到内存中
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(goodsVo -> {
            log.info("现有库存为：" + goodsVo.getStockCount());
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            EmptyStockMap.put(goodsVo.getId(), false);
        });
    }
}
