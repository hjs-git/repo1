package com.itheima.rabbitmq.routing;

import com.itheima.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 路由模式：发送两条消息
 insert和update */
public class Producer {

    //交换机名称
    static final String DIRECT_EXCHANGE = "direct_exchange";
    //队列名称
    static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    public static void main(String[] args) throws Exception {
        //1.创建连接
        Connection connection = ConnectionUtil.getConnection();
        //2.创建频道
        Channel channel = connection.createChannel();
        //3.声明交换机
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        //4.声明队列
        /**
         参数1：队列名称
         参数2：是否定义持久化队列
         参数3：是否独占本连接
         参数4：是否在不使用的时候队列自动删除
         参数5：其他参数
         */
        channel.queueDeclare(DIRECT_QUEUE_INSERT, true, false, false, null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE, true, false, false, null);
        //5.队列绑定到交换机
        channel.queueBind(DIRECT_QUEUE_INSERT, DIRECT_EXCHANGE, "insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE, DIRECT_EXCHANGE, "update");
        //6.发送消息
        String message = "Hello RabbitMQ。路由模式 ；路由key为insert";
        /**
         参数1：交换机名称，如果没有则使用空字符串
         参数2：路由key，简单模式中可以使用队列名称
         参数3：消息其他属性
         参数4：消息内容
         */
        channel.basicPublish(DIRECT_EXCHANGE, "insert", null, message.getBytes());
        System.out.println("已发送消息:" + message);

        message = "Hello RabbitMQ。路由模式 ；路由key为update";
        channel.basicPublish(DIRECT_EXCHANGE, "update", null, message.getBytes());
        System.out.println("已发送消息:" + message);

        //7.关闭资源
        channel.close();
        connection.close();
    }

}
