package com.oppo.seckilldemo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送秒杀信息
    public void sendSeckillMessage(String message){
        log.info("发送秒杀消息：" + message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", message);
    }



    /**
     *
     * RabbitMQ常用模式示例
     *
     */

    // 发送消息到队列
    public void send(Object msg){
        log.info("发送消息：" + msg);
        rabbitTemplate.convertAndSend("queue", msg);
    }
    // 发送消息到交换机
    public void sendToExchange(Object msg){
        log.info("发送消息：" + msg);
        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
    }
    // 发送消息到交换机
    public void sendToDirectExchange01(Object msg){
        log.info("发送红色消息：" + msg);
        rabbitTemplate.convertAndSend("directExchange", "queue.red", msg);
    }
    // 发送消息到交换机
    public void sendToDirectExchange02(Object msg){
        log.info("发送绿色消息：" + msg);
        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
    }
    // 发送消息到交换机
    public void sendToTopicExchange01(Object msg){
        log.info("发送Topic消息：" + msg);
        rabbitTemplate.convertAndSend("topicExchange", "queue.red.message", msg);
    }
    // 发送消息到交换机
    public void sendToTopicExchange02(Object msg){
        log.info("发送Topic消息：" + msg);
        rabbitTemplate.convertAndSend("topicExchange", "message.queue.green", msg);
    }
    // 发送消息到交换机
    public void sendToHeadersExchange01(String msg){
        log.info("发送Headers消息：" + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("color","red");
        properties.setHeader("speed","fast");
        Message message = new Message(msg.getBytes(), properties);
        rabbitTemplate.convertAndSend("headersExchange", "", message);
    }
    // 发送消息到交换机
    public void sendToHeadersExchange02(String msg){
        log.info("发送Headers消息：" + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("color","red");
        properties.setHeader("speed","normal");
        Message message = new Message(msg.getBytes(), properties);
        rabbitTemplate.convertAndSend("headersExchange", "", message);
    }

}
