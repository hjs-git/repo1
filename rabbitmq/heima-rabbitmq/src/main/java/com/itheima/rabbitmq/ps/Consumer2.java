package com.itheima.rabbitmq.ps;

import com.itheima.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 发布与订阅模式：消费者接受消息
 */
public class Consumer2 {
    public static void main(String[] args) throws Exception{
        //1.创建连接
        Connection connection = ConnectionUtil.getConnection();
        //2.创建频道
        Channel channel = connection.createChannel();
        //3.声明交换机
        channel.exchangeDeclare(Producer.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        //4.声明队列
        channel.queueDeclare(Producer.FANOUT_QUEUE_2,true,false,false,null);
        //5.绑定队列到交换机上
        channel.queueBind(Producer.FANOUT_QUEUE_2,Producer.FANOUT_EXCHANGE,"");
        //6.创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //路由key
                System.out.println("路由key为："+envelope.getRoutingKey());
                //交换机
                System.out.println("交换机为"+envelope.getExchange());
                //消息id
                System.out.println("消息id为"+envelope.getDeliveryTag());
                //接收到的消息
                System.out.println("消费者2-接收到的消息为："+new String(body,"utf-8"));
            }
        };
        //7.监听队列
        channel.basicConsume(Producer.FANOUT_QUEUE_2,true,defaultConsumer);
    }
}
