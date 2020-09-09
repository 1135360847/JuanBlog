package com.zzw.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：zhangzhiwen
 * @date ：Created in 2019/10/21 19:40
 * @description：mq消息发送方测试
 * @modified By：
 */
@Component
public class MqSenderTest extends OrderApplicationTests{
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//
//    @Test
//    public void send(){
//        amqpTemplate.convertAndSend("myQueue","now"+new Date());
//    }
//
//    @Test
//    public void sendOrder(){
//        amqpTemplate.convertAndSend("myOrder","computer","now"+new Date());
//    }

}
