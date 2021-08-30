package com.oppo.seckilldemo.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.oppo.seckilldemo.pojo.SeckillMessage;
import com.oppo.seckilldemo.pojo.SeckillOrder;
import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.service.IGoodsService;
import com.oppo.seckilldemo.service.IOrderService;
import com.oppo.seckilldemo.vo.GoodsVo;
import com.oppo.seckilldemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    IGoodsService goodsService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IOrderService orderService;

    //下单操作
    @RabbitListener(queues = "seckillQueue")
    public void receiveSeckillMessage(String message){
        log.info("接收的秒杀消息：" + message);
        SeckillMessage seckillMessage = JSONObject.parseObject(message, SeckillMessage.class);
        Long goodId = seckillMessage.getGoodId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodId);
        if (goodsVo.getStockCount() < 1){
            return;
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder)redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodId);
        if(seckillOrder != null){
            return;
        }
        orderService.seckill(user, goodsVo);
    }


    /**
     *
     * RabbitMQ常用模式示例
     *
     */

    @RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_fanout01")
    public void receive01(Object msg){
        log.info("QUEUE01接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_fanout02")
    public void receive02(Object msg){
        log.info("QUEUE02接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_direct01")
    public void receive03(Object msg){
        log.info("红色QUEUE01接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_direct02")
    public void receive04(Object msg){
        log.info("绿色QUEUE02接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_topic01")
    public void receive05(Object msg){
        log.info("QUEUE01接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_topic02")
    public void receive06(Object msg){
        log.info("QUEUE02接收消息：" + msg);
    }

    @RabbitListener(queues = "queue_head01")
    public void receive07(Message message){
        log.info("QUEUE01接收Message：" + message);
        log.info("QUEUE01消息" + new String(message.getBody()));
    }

    @RabbitListener(queues = "queue_head02")
    public void receive08(Message message){
        log.info("QUEUE02接收Message：" + message);
        log.info("QUEUE02消息" + new String(message.getBody()));
    }

}
