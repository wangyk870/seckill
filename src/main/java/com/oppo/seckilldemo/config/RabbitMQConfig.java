package com.oppo.seckilldemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitMQConfig {

    private static final String SECKILL_QUEUE = "seckillQueue";
    private static final String SECKILL_EXCHANGE = "seckillExchange";

    @Bean
    public Queue seckillQueue(){
        return new Queue(SECKILL_QUEUE);
    }
    @Bean
    public TopicExchange seckillExchange(){
        return new TopicExchange(SECKILL_EXCHANGE);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(seckillQueue()).to(seckillExchange()).with("seckill.#");
    }

    /**
     *
     * RabbitMQ常用模式示例
     *
     */

    private static final String QUEUE01 = "queue_fanout01";
    private static final String QUEUE02 = "queue_fanout02";
    private static final String DIRECT01 = "queue_direct01";
    private static final String DIRECT02 = "queue_direct02";
    private static final String TOPIC01 = "queue_topic01";
    private static final String TOPIC02 = "queue_topic02";
    private static final String HEAD01 = "queue_head01";
    private static final String HEAD02 = "queue_head02";
    private static final String EXCHANGE = "fanoutExchange";
    private static final String DIRECT_EXCHANGE = "directExchange";
    private static final String TOPIC_EXCHANGE = "topicExchange";
    private static final String HEAD_EXCHANGE = "headersExchange";
    private static final String DIRROUTINGKEY01 = "queue.red";
    private static final String DIRROUTINGKEY02 = "queue.green";
    private static final String TOPICROUTINGKEY01 = "#.queue.#";
    private static final String TOPICROUTINGKEY02 = "*.queue.#";

    //普通消息队列
    @Bean
    public Queue queue(){
        return new Queue("queue", true);
    }
    //交换机加消息队列
    @Bean
    public Queue queue01(){
        return new Queue(QUEUE01);
    }
    @Bean
    public Queue queue02(){
        return new Queue(QUEUE02);
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE);
    }
    @Bean
    public Binding binding01(){
        return BindingBuilder.bind(queue01()).to(fanoutExchange());
    }
    @Bean
    public Binding binding02(){
        return BindingBuilder.bind(queue02()).to(fanoutExchange());
    }
    //Direct模式交换机消息对列
    @Bean
    public Queue queueDir01(){
        return new Queue(DIRECT01);
    }
    @Bean
    public Queue queueDir02(){
        return new Queue(DIRECT02);
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }
    @Bean
    public Binding bindingDir01(){
        return BindingBuilder.bind(queueDir01()).to(directExchange()).with(DIRROUTINGKEY01);
    }
    @Bean
    public Binding bindingDir02(){
        return BindingBuilder.bind(queueDir02()).to(directExchange()).with(DIRROUTINGKEY02);
    }

    //Topic模式交换机消息对列
    @Bean
    public Queue queueTopic01(){
        return new Queue(TOPIC01);
    }
    @Bean
    public Queue queueTopic02(){
        return new Queue(TOPIC02);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }
    @Bean
    public Binding bindingTopic01(){
        return BindingBuilder.bind(queueTopic01()).to(topicExchange()).with(TOPICROUTINGKEY01);
    }
    @Bean
    public Binding bindingTopic02(){
        return BindingBuilder.bind(queueTopic02()).to(topicExchange()).with(TOPICROUTINGKEY02);
    }

    //Head模式
    @Bean
    public Queue queueHead01(){
        return new Queue(HEAD01);
    }
    @Bean
    public Queue queueHead02(){
        return new Queue(HEAD02);
    }
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEAD_EXCHANGE);
    }
    @Bean
    public Binding bindingHeaders01(){
        Map<String, Object> map = new HashMap<>();
        map.put("color","red");
        map.put("speed","low");
        return BindingBuilder.bind(queueHead01()).to(headersExchange()).whereAny(map).match();
    }
    @Bean
    public Binding bindingHeaders02(){
        Map<String, Object> map = new HashMap<>();
        map.put("color","red");
        map.put("speed","fast");
        return BindingBuilder.bind(queueHead02()).to(headersExchange()).whereAll(map).match();
    }

}
