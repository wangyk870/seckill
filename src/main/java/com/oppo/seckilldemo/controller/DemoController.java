package com.oppo.seckilldemo.controller;

import com.oppo.seckilldemo.rabbitmq.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    MQSender mqSender;

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","i love you !");
        return "hello";
    }

    @RequestMapping("/send")
    @ResponseBody
    public void send(){
        mqSender.send("hello MQ !!");
    }

    @RequestMapping("/sendToExchange")
    @ResponseBody
    public void sendToExchange(){
        mqSender.sendToExchange("hello MQ Exchange !!");
    }

    @RequestMapping("/sendToDirectExchange01")
    @ResponseBody
    public void sendToDirectExchange01(){
        mqSender.sendToDirectExchange01("hello Red MQ Exchange !!");
    }

    @RequestMapping("/sendToDirectExchange02")
    @ResponseBody
    public void sendToDirectExchange02(){
        mqSender.sendToDirectExchange02("hello Green MQ Exchange !!");
    }

    @RequestMapping("/sendToTopicExchange01")
    @ResponseBody
    public void sendToTopicExchange01(){
        mqSender.sendToTopicExchange01("hello Red MQ Exchange !!");
    }

    @RequestMapping("/sendToTopicExchange02")
    @ResponseBody
    public void sendToTopicExchange02(){
        mqSender.sendToTopicExchange02("hello Green MQ Exchange !!");
    }

    @RequestMapping("/sendToHeadersExchange01")
    @ResponseBody
    public void sendToHeadersExchange01(){
        mqSender.sendToHeadersExchange01("hello Red and Fast MQ Exchange !!");
    }

    @RequestMapping("/sendToHeadersExchange02")
    @ResponseBody
    public void sendToHeadersExchange02(){
        mqSender.sendToHeadersExchange02("hello Red and Normal MQ Exchange !!");
    }
}
